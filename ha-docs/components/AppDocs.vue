<template>
  <div class="flow-docs" :class="{ 'flow-docs--batch': hasBatchSections }">
    <template v-if="hasBatchSections">
      <v-card
        v-for="section in flowSections"
        :key="section.id"
        class="flow-card"
        :class="{ 'flow-card--shell': section.kind === 'shell' }"
        variant="outlined"
      >
        <v-card-title class="flow-card__title">
          {{ section.title }}
        </v-card-title>
        <v-card-text class="flow-card__body">
          <div
            class="flow-card__viewport"
            tabindex="0"
            role="region"
            :aria-label="`${section.title}の処理フロー`"
          >
            <Mermaid
              class="flow-card__diagram"
              :nodes="section.nodes"
              type="graph TB"
              natural-size
            />
          </div>
        </v-card-text>
      </v-card>
    </template>
    <v-card v-else>
      <v-card-text class="text-subtitle-1">処理フロー</v-card-text>
      <v-card-text>
        <div
          class="flow-sections"
          :class="{ 'flow-sections--single': flowSections.length <= 1 }"
        >
          <div
            v-for="(section, index) in flowSections"
            :key="index"
            class="flow-section"
          >
            <Mermaid :nodes="section.nodes" type="graph TB" />
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {
    flow: Array,
  },
  computed: {
    flowSections() {
      const nodes = Array.isArray(this.flow) ? this.flow : [];
      if (!nodes.length) {
        return [];
      }

      const getId = (node) => String(node?.id || "").trim();
      const isCheckDetail = (node) =>
        typeof node?.group === "string" && node.group.includes("チェック");
      const normalNodes = nodes.filter((node) => getId(node) && !isCheckDetail(node));
      const checkNodes = nodes.filter((node) => getId(node) && isCheckDetail(node));

      const buildSection = (sectionNodes) => {
        const ids = new Set(sectionNodes.map((node) => getId(node)));
        const kind = sectionNodes.every((node) => getId(node).startsWith("sh_"))
          ? "shell"
          : sectionNodes.every((node) => getId(node).startsWith("java_"))
            ? "java"
            : "flow";
        const title = {
          shell: "シェル処理",
          java: "Java処理",
          flow: "処理フロー",
        }[kind];
        return {
          id: getId(sectionNodes[0]),
          kind,
          title,
          nodes: sectionNodes.map((node) => ({
            ...node,
            next: Array.isArray(node.next)
              ? node.next.filter((nextId) => ids.has(String(nextId || "").trim()))
              : node.next,
          })),
        };
      };

      const normalIds = new Set(normalNodes.map((node) => getId(node)));
      const adjacency = new Map();
      for (const id of normalIds) {
        adjacency.set(id, new Set());
      }
      for (const node of normalNodes) {
        const from = getId(node);
        const nexts = Array.isArray(node.next) ? node.next : [];
        for (const nextId of nexts) {
          const to = String(nextId || "").trim();
          if (!normalIds.has(to)) {
            continue;
          }
          adjacency.get(from).add(to);
          adjacency.get(to).add(from);
        }
      }

      const sections = [];
      const visited = new Set();
      for (const node of normalNodes) {
        const start = getId(node);
        if (visited.has(start)) {
          continue;
        }

        const stack = [start];
        const componentIds = new Set();
        while (stack.length) {
          const id = stack.pop();
          if (visited.has(id)) {
            continue;
          }
          visited.add(id);
          componentIds.add(id);
          for (const nextId of adjacency.get(id) || []) {
            if (!visited.has(nextId)) {
              stack.push(nextId);
            }
          }
        }

        sections.push(
          buildSection(normalNodes.filter((item) => componentIds.has(getId(item))))
        );
      }

      const checkGroups = [];
      const checkGroupMap = new Map();
      for (const node of checkNodes) {
        const groupName = node.group || "チェック処理";
        if (!checkGroupMap.has(groupName)) {
          const group = [];
          checkGroupMap.set(groupName, group);
          checkGroups.push(group);
        }
        checkGroupMap.get(groupName).push(node);
      }
      for (const group of checkGroups) {
        sections.push(buildSection(group));
      }

      return sections;
    },
    hasBatchSections() {
      return this.flowSections.some(
        (section) => section.kind === "shell" || section.kind === "java"
      );
    },
  },
};
</script>

<style scoped>
.flow-docs {
  min-width: 0;
  max-width: 100%;
}

.flow-docs--batch {
  display: grid;
  grid-template-columns: minmax(0, 1fr);
  gap: 24px;
}

.flow-card {
  min-width: 0;
  border-color: #d5e1e5;
  background: rgb(var(--v-theme-surface));
}

.flow-card__title {
  padding: 16px 20px;
  border-bottom: 1px solid #d5e1e5;
  background: #f4f9fa;
  color: #006d77;
  font-size: 1.125rem;
  font-weight: 600;
}

.flow-card__body {
  padding: 0;
}

.flow-card__viewport {
  box-sizing: border-box;
  width: 100%;
  min-width: 0;
  max-width: 100%;
  max-height: min(75vh, 900px);
  overflow: auto;
  padding: 24px;
  scrollbar-gutter: stable;
}

.flow-card__viewport:focus-visible {
  outline: 2px solid #0097a7;
  outline-offset: -2px;
}

.flow-card--shell .flow-card__viewport {
  max-height: min(50vh, 650px);
}

.flow-card__diagram {
  width: max-content;
  min-width: 100%;
}

.flow-card__diagram :deep(svg) {
  display: block;
  margin-inline: auto;
}

.flow-sections {
  display: flex;
  align-items: flex-start;
  gap: 24px;
  overflow-x: auto;
  padding-bottom: 8px;
}

.flow-sections--single {
  display: block;
  overflow-x: visible;
}

.flow-section {
  flex: 0 0 auto;
  min-width: max-content;
}
</style>
