<template>
    <div ref="el" />
</template>
<script>
export default {
    name: 'Mermaid',
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
    watch: {
        code() {
            this.render()
        },
        type() {
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
            const code = this.resolvedCode;
            console.log('[Mermaid] rendering code=', code)
            if (!code) {
                // デバッグしやすく
                console.warn('[Mermaid] empty code')
                this.$refs.el.innerHTML = ''
                return
            }
            const id = 'mmd-' + Math.random().toString(36).slice(2);
            try {
                const { svg } = await this.$mermaid.render(id, code);
                this.$refs.el.innerHTML = svg;
            } catch (e) {
                console.error('[Mermaid] render failed', e, { code });
                this.$refs.el.innerHTML = '<pre style="color:#c00">Mermaid parse error</pre>';
            }
        }
    }
}
</script>