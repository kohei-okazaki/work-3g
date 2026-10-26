import assert from 'node:assert/strict';
import { readFile } from 'node:fs/promises';
import test from 'node:test';
import { parse } from 'vue/compiler-sfc';

const source = await readFile(new URL('../components/AppDocs.vue', import.meta.url), 'utf8');
const { descriptor } = parse(source);
const { default: component } = await import(`data:text/javascript;base64,${Buffer.from(descriptor.script.content).toString('base64')}`);

function sectionsFor(flow) {
    const flowSections = component.computed.flowSections.call({ flow });
    const hasBatchSections = component.computed.hasBatchSections.call({ flowSections });
    return { flowSections, hasBatchSections };
}

function connections(section) {
    return section.nodes.map(node => [node.id, node.next || []]);
}

test('separates shell and Java diagrams while preserving branches and loops', () => {
    const { flowSections, hasBatchSections } = sectionsFor([
        { id: 'sh_start', next: ['sh_lock'] },
        { id: 'sh_lock', next: ['sh_run', 'sh_busy'] },
        { id: 'sh_run' },
        { id: 'sh_busy' },
        { id: 'java_start', next: ['java_poll'] },
        { id: 'java_poll', next: ['java_poll', 'java_end'] },
        { id: 'java_end' },
    ]);

    assert.equal(flowSections.length, 2);
    assert.equal(hasBatchSections, true);
    assert.deepEqual(
        flowSections.map(({ id, kind, title }) => ({ id, kind, title })),
        [
            { id: 'sh_start', kind: 'shell', title: 'シェル処理' },
            { id: 'java_start', kind: 'java', title: 'Java処理' },
        ]
    );
    assert.deepEqual(connections(flowSections[0]), [
        ['sh_start', ['sh_lock']],
        ['sh_lock', ['sh_run', 'sh_busy']],
        ['sh_run', []],
        ['sh_busy', []],
    ]);
    assert.deepEqual(connections(flowSections[1]), [
        ['java_start', ['java_poll']],
        ['java_poll', ['java_poll', 'java_end']],
        ['java_end', []],
    ]);
});

test('keeps a shell-only flow in one section without an empty Java section', () => {
    const { flowSections, hasBatchSections } = sectionsFor([
        { id: 'sh_start', next: ['sh_end'] },
        { id: 'sh_end' },
    ]);

    assert.equal(hasBatchSections, true);
    assert.equal(flowSections.length, 1);
    assert.equal(flowSections[0].id, 'sh_start');
    assert.equal(flowSections[0].kind, 'shell');
    assert.equal(flowSections[0].title, 'シェル処理');
    assert.deepEqual(connections(flowSections[0]), [
        ['sh_start', ['sh_end']],
        ['sh_end', []],
    ]);
});

test('preserves ordinary connected components and separate check-detail groups', () => {
    const flow = [
        { id: 'api_start', next: ['api_end', 'required'] },
        { id: 'api_end' },
        { id: 'screen_start', next: ['screen_end'] },
        { id: 'screen_end' },
        { id: 'required', group: '入力チェック', next: ['format', 'api_end'] },
        { id: 'format', group: '入力チェック' },
        { id: 'permission', group: '利用チェック' },
    ];
    const { flowSections, hasBatchSections } = sectionsFor(flow);

    assert.equal(hasBatchSections, false);
    assert.equal(flowSections.length, 4);
    assert.deepEqual(flowSections.map(section => section.id), [
        'api_start', 'screen_start', 'required', 'permission',
    ]);
    assert(flowSections.every(section => section.kind === 'flow'));
    assert(flowSections.every(section => section.title === '処理フロー'));
    assert.deepEqual(connections(flowSections[0]), [
        ['api_start', ['api_end']],
        ['api_end', []],
    ]);
    assert.deepEqual(connections(flowSections[1]), [
        ['screen_start', ['screen_end']],
        ['screen_end', []],
    ]);
    assert.deepEqual(connections(flowSections[2]), [
        ['required', ['format']],
        ['format', []],
    ]);
    assert.deepEqual(connections(flowSections[3]), [['permission', []]]);
    assert.deepEqual(flow[0].next, ['api_end', 'required']);
    assert.deepEqual(flow[4].next, ['format', 'api_end']);
});

test('does not create diagram sections for empty or missing flow input', () => {
    for (const flow of [[], undefined, null]) {
        const { flowSections, hasBatchSections } = sectionsFor(flow);
        assert.deepEqual(flowSections, []);
        assert.equal(hasBatchSections, false);
    }
});
