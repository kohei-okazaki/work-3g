import assert from 'node:assert/strict';
import { readFile } from 'node:fs/promises';
import test from 'node:test';
import { parse } from 'vue/compiler-sfc';

const source = await readFile(new URL('../components/Mermaid.vue', import.meta.url), 'utf8');
const { descriptor } = parse(source);
const { default: component } = await import(`data:text/javascript;base64,${Buffer.from(descriptor.script.content).toString('base64')}`);

function setup() {
    const pending = [];
    const el = { innerHTML: '' };
    const instance = {
        ...component.data(),
        $refs: { el },
        naturalSize: false,
        resolvedCode: 'graph TB\na-->b',
        $mermaid: {
            render: () => new Promise((resolve, reject) => pending.push({ resolve, reject })),
        },
    };
    return { instance, el, pending, render: () => component.methods.render.call(instance) };
}

test('renders a completed diagram', async () => {
    const { el, pending, render } = setup();
    const task = render();
    pending[0].resolve({ svg: '<svg>diagram</svg>' });
    await task;
    assert.equal(el.innerHTML, '<svg>diagram</svg>');
});

test('ignores a completed render after navigating away', async () => {
    const { instance, el, pending, render } = setup();
    const task = render();
    component.beforeUnmount.call(instance);
    instance.$refs.el = null;
    pending[0].resolve({ svg: '<svg>old</svg>' });
    await assert.doesNotReject(task);
    assert.equal(el.innerHTML, '');
});

test('ignores a rejected render after navigating away', async () => {
    const { instance, el, pending, render } = setup();
    const task = render();
    component.beforeUnmount.call(instance);
    instance.$refs.el = null;
    pending[0].reject(new Error('render failed'));
    await assert.doesNotReject(task);
    assert.equal(el.innerHTML, '');
});

test('an older render cannot overwrite a newer diagram', async () => {
    const { instance, el, pending, render } = setup();
    const first = render();
    instance.resolvedCode = 'graph TB\nx-->y';
    const second = render();
    pending[1].resolve({ svg: '<svg>new</svg>' });
    await second;
    pending[0].resolve({ svg: '<svg>old</svg>' });
    await first;
    assert.equal(el.innerHTML, '<svg>new</svg>');
});

test('clearing the diagram invalidates an unfinished render', async () => {
    const { instance, el, pending, render } = setup();
    el.innerHTML = '<svg>previous</svg>';
    const task = render();
    instance.resolvedCode = '';
    await render();
    assert.equal(el.innerHTML, '');
    pending[0].resolve({ svg: '<svg>old</svg>' });
    await task;
    assert.equal(el.innerHTML, '');
});

function createSvg(viewBox) {
    const attributes = new Map([['width', '100%']]);
    if (viewBox !== null) attributes.set('viewBox', viewBox);
    return {
        attributes,
        style: { maxWidth: '940.5px' },
        getAttribute: (name) => attributes.get(name) ?? null,
        setAttribute: (name, value) => attributes.set(name, value),
    };
}

test('natural size preserves viewBox dimensions and removes responsive scaling', async () => {
    const { instance, el, pending, render } = setup();
    const diagram = createSvg('-8 -8 940.5 1280');
    instance.naturalSize = true;
    el.querySelector = (selector) => {
        assert.equal(selector, 'svg');
        assert.equal(el.innerHTML, '<svg>diagram</svg>');
        return diagram;
    };
    const task = render();
    pending[0].resolve({ svg: '<svg>diagram</svg>' });
    await task;
    assert.equal(diagram.attributes.get('width'), '940.5');
    assert.equal(diagram.attributes.get('height'), '1280');
    assert.deepEqual(diagram.style, {
        width: '940.5px',
        height: '1280px',
        maxWidth: 'none',
    });
});

test('responsive mode does not inspect or resize the rendered SVG', async () => {
    const { el, pending, render } = setup();
    let inspected = false;
    el.querySelector = () => { inspected = true; };
    const svg = '<svg width="100%" style="max-width: 940.5px">diagram</svg>';
    const task = render();
    pending[0].resolve({ svg });
    await task;
    assert.equal(inspected, false);
    assert.equal(el.innerHTML, svg);
});

test('natural size safely leaves missing or invalid viewBoxes unchanged', async () => {
    const cases = [
        null,
        createSvg(null),
        createSvg(''),
        createSvg('0 0 invalid 100'),
        createSvg('0 0 0 100'),
        createSvg('0 0 100 -1'),
        createSvg('0 0 Infinity 100'),
        createSvg('0 0 100 200 300'),
    ];
    for (const diagram of cases) {
        const { instance, el, pending, render } = setup();
        instance.naturalSize = true;
        el.querySelector = () => diagram;
        const task = render();
        pending[0].resolve({ svg: '<svg>diagram</svg>' });
        await assert.doesNotReject(task);
        assert.equal(el.innerHTML, '<svg>diagram</svg>');
        if (diagram) {
            assert.equal(diagram.attributes.get('width'), '100%');
            assert.equal(diagram.attributes.has('height'), false);
            assert.deepEqual(diagram.style, { maxWidth: '940.5px' });
        }
    }
});
