import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { createRequire } from "node:module";
import test from "node:test";

const require = createRequire(import.meta.url);
const { parse } = require("@vue/compiler-sfc");
const source = readFileSync(
  new URL("../pages/user/entry/index.vue", import.meta.url),
  "utf8",
);
const { descriptor, errors } = parse(source);
assert.deepEqual(errors, []);
const script = descriptor.script.content
  .replace(/^import axios from "axios";\r?$/m, "")
  .replace("export default", "return");

function createPage(post) {
  const component = new Function("axios", "process", script)(
    { post },
    { env: { api_base_url: "https://example.invalid/api/root/" } },
  );
  const page = component.data();
  page.password = "test-password";
  page.confPassword = "test-password";
  return { page, submit: () => component.methods.entryUser.call(page) };
}

test("registration waits for the API and clears a previous error on success", async () => {
  let resolveRequest;
  const request = new Promise((resolve) => { resolveRequest = resolve; });
  let sentRequest;
  const { page, submit } = createPage((...args) => {
    sentRequest = args;
    return request;
  });
  page.error = { hasError: true, message: "previous failure" };
  const pending = submit();
  assert.equal(page.loading, true);
  assert.equal(page.error.hasError, false);
  assert.equal(page.error.message, null);
  assert.deepEqual(sentRequest, [
    "https://example.invalid/api/root/user",
    { password: "test-password", conf_password: "test-password" },
  ]);
  resolveRequest({ data: { result: "0", seq_login_id: 123 } });
  await pending;
  assert.deepEqual(page.api_data, { api_result: "0", seq_login_id: 123 });
  assert.equal(page.loading, false);
  assert.equal(page.error.hasError, false);
});

test("business errors display the API errors array and release loading", async () => {
  const { page, submit } = createPage(async () => ({
    data: { result: "1", errors: [{ message: "first error" }, { message: "second error" }] },
  }));
  await submit();
  assert.equal(page.error.hasError, true);
  assert.equal(page.error.message, "first error\nsecond error");
  assert.equal(page.loading, false);
});

test("a legacy single error message is still displayed", async () => {
  const { page, submit } = createPage(async () => ({
    data: { result: "1", error: { message: "legacy error" } },
  }));
  await submit();
  assert.equal(page.error.message, "legacy error");
  assert.equal(page.loading, false);
});

test("business errors without details use a fallback message", async () => {
  const { page, submit } = createPage(async () => ({ data: { result: "1" } }));
  await submit();
  assert.equal(page.error.hasError, true);
  assert.equal(page.error.message, "ユーザ作成に失敗しました。");
  assert.equal(page.loading, false);
});

test("HTTP errors display the backend validation message and release loading", async () => {
  const { page, submit } = createPage(async () => {
    const error = new Error("Request failed with status code 400");
    error.response = { data: { result: "1", errors: [{ message: "password mismatch" }] } };
    throw error;
  });
  await submit();
  assert.equal(page.error.hasError, true);
  assert.equal(page.error.message, "password mismatch");
  assert.equal(page.loading, false);
});

test("network errors without a response release loading", async () => {
  const { page, submit } = createPage(async () => { throw new Error("Network Error"); });
  await submit();
  assert.equal(page.error.hasError, true);
  assert.equal(page.error.message, "Network Error");
  assert.equal(page.loading, false);
});

test("an unexpected response also releases loading", async () => {
  const { page, submit } = createPage(async () => ({ data: null }));
  await submit();
  assert.equal(page.error.hasError, true);
  assert.equal(page.error.message, "ユーザ作成に失敗しました。");
  assert.equal(page.loading, false);
});

