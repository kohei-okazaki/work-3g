export default defineNuxtPlugin(async () => {
  try {
    const mod = await import("mermaid");
    const mermaid = mod.default || mod;

    mermaid.initialize({
      startOnLoad: false,
      securityLevel: "loose",
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
