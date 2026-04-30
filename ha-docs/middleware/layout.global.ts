const layoutRules = [
  { prefix: "/wiki", layout: "wiki-layout" },
  { prefix: "/db", layout: "db-layout" },
  {
    prefix: "/healthinfoapp/batch",
    layout: "healthinfoapp-batch-layout",
  },
  {
    prefix: "/healthinfoapp/api",
    layout: "healthinfoapp-api-layout",
  },
  {
    prefix: "/healthinfoapp/dashboard",
    layout: "healthinfoapp-dashboard-layout",
  },
  { prefix: "/root/api", layout: "root-api-layout" },
  { prefix: "/calc_api", layout: "calc-api-layout" },
];

export default defineNuxtRouteMiddleware((to) => {
  const matchedRule = layoutRules.find(
    ({ prefix }) => to.path === prefix || to.path.startsWith(`${prefix}/`),
  );

  setPageLayout(matchedRule?.layout ?? "default");
});
