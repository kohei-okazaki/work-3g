export default async (_ctx, inject) => {
  try {
    const mod = await import('mermaid')   // ← ESMをimport
    const mermaid = mod.default || mod
    mermaid.initialize({ startOnLoad: false, securityLevel: 'loose' })
    inject('mermaid', mermaid)
  } catch (e) {
    console.error('[mermaid plugin failed]', e)
    inject('mermaid', { render: async () => ({ svg: '' }) }) // フェイルセーフ
  }
}