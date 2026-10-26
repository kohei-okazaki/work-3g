export default defineNuxtPlugin(async () => {
  try {
    const mod = await import("mermaid");
    const mermaid = mod.default || mod;

    mermaid.initialize({
      startOnLoad: false,
      securityLevel: "antiscript",
      // Preserve the diagram layout and appearance used before Mermaid 12.
      layout: "dagre",
      theme: "default",
      look: "classic",
    });

    return {
      provide: {
        mermaid,
      },
    };
  } catch (error) {
    console.error("[mermaid plugin failed]", error);

    return {
      provide: {
        mermaid: {
          render: async () => ({ svg: "" }),
        },
      },
    };
  }
});
