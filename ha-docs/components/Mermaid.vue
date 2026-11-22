<template>
    <div ref="el" />
</template>
<script>
export default {
    name: 'Mermaid',
    data() {
        return { renderVersion: 0 };
    },
    props: {
        // 新規は code 推奨。旧API互換で nodes/type も受ける
        code: String,
        // [{id,text,edgeType,next: string[]}]
        nodes: {
            type: Array,
            default: () => []
        },
        type: {
            type: String,
            default: 'graph TB'
        },
        naturalSize: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        resolvedCode() {
            // code を最優先（素の文字列DSL）
            if (this.code && this.code.trim()) {
                return this.code.trim();
            }

            // nodes が配列：文字列 / {from,to,label,dir} / {id,text,next,edgeType} に対応
            if (Array.isArray(this.nodes) && this.nodes.length) {
                const decl = [];
                const edges = [];
                for (const n of this.nodes) {
                    if (!n || typeof n !== 'object') {
                        continue;
                    }
                    const id = String(n.id || '').trim()
                    if (!id) {
                        continue;
                    }
                    const label = String(n.text ?? id).replace(/\n/g, '<br/>')
                    const shape = String(n.edgeType || 'rect').toLowerCase()
                    let body
                    switch (shape) {
                        case 'circle': body = `(( ${label} ))`; break
                        case 'round':
                        case 'rounded': body = `( ${label} )`; break
                        case 'diamond':
                        case 'decision': body = `{ ${label} }`; break
                        default: body = `[ ${label} ]`
                    }
                    decl.push(`${id}${body}`);

                    const nexts = Array.isArray(n.next) ? n.next : []
                    for (const to of nexts) {
                        const t = String(to || '').trim();
                        if (t) {
                            edges.push(`${id}-->${t}`);
                        }
                    }
                }
                const lines = [...decl, ...edges].filter(Boolean)
                if (lines.length) return `${this.type}\n${lines.join('\n')}`
            }
            return ''
        }
    },
    mounted() { 
        this.render() 
    },
    beforeUnmount() {
        this.renderVersion++;
    },
    watch: {
        code() {
            this.render()
        },
        type() {
            this.render()
        },
        naturalSize() {
            this.render()
        },
        nodes: {
            handler() {
                this.render()
            },
            deep: true
        }
    },
    methods: {
        async render() {
            const version = ++this.renderVersion;
            const el = this.$refs.el;
            if (!el) return;
            const code = this.resolvedCode;
            if (!code) {
                el.innerHTML = ''
                return
            }
            const id = 'mmd-' + Math.random().toString(36).slice(2);
            try {
                const { svg } = await this.$mermaid.render(id, code);
                if (version !== this.renderVersion || this.$refs.el !== el) return;
                el.innerHTML = svg;
                if (this.naturalSize) {
                    const diagram = el.querySelector?.('svg');
                    const viewBox = diagram?.getAttribute('viewBox')?.trim();
                    const dimensions = viewBox ? viewBox.split(/[\s,]+/).map(Number) : [];
                    if (dimensions.length !== 4 || !dimensions.every(Number.isFinite)
                        || dimensions[2] <= 0 || dimensions[3] <= 0) return;
                    const [, , width, height] = dimensions;
                    diagram.setAttribute('width', String(width));
                    diagram.setAttribute('height', String(height));
                    diagram.style.width = `${width}px`;
                    diagram.style.height = `${height}px`;
                    diagram.style.maxWidth = 'none';
                }
            } catch (e) {
                if (version !== this.renderVersion || this.$refs.el !== el) return;
                console.error('[Mermaid] render failed', e, { code });
                el.innerHTML = '<pre style="color:#c00">Mermaid parse error</pre>';
            }
        }
    }
}
</script>
