<template>
  <div>
    <v-card>
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
        return {
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
  },
};
</script>

<style scoped>
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