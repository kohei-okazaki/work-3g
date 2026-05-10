(function() {
  function ready(callback) {
    if (document.readyState === "loading") {
      document.addEventListener("DOMContentLoaded", callback);
      return;
    }

    callback();
  }

  ready(function() {
    var menu = document.getElementById("dropmenu");

    if (!menu) {
      return;
    }

    var mobileQuery = window.matchMedia("(max-width: 767.98px)");

    function closeItem(item) {
      item.classList.remove("is-open");
      var links = item.querySelectorAll("li > a[aria-expanded]");
      for (var i = 0; i < links.length; i += 1) {
        links[i].setAttribute("aria-expanded", "false");
      }
    }

    function closeAll() {
      var openItems = menu.querySelectorAll("li.is-open");
      for (var i = 0; i < openItems.length; i += 1) {
        closeItem(openItems[i]);
      }
    }

    function getDirectSubMenu(item) {
      for (var i = 0; i < item.children.length; i += 1) {
        if (item.children[i].tagName.toLowerCase() === "ul") {
          return item.children[i];
        }
      }
      return null;
    }

    function getDirectLink(item) {
      for (var i = 0; i < item.children.length; i += 1) {
        if (item.children[i].tagName.toLowerCase() === "a") {
          return item.children[i];
        }
      }
      return null;
    }

    function isPlaceholderLink(link) {
      var href = link.getAttribute("href");
      return !href || href === "#";
    }

    function shouldUseNativeHandler(link) {
      var href = link.getAttribute("href");
      return !href || href.indexOf("#") === 0 || link.hasAttribute("data-toggle");
    }

    function getSiblingItems(item) {
      return Array.prototype.filter.call(item.parentElement.children, function(child) {
        return child !== item && child.tagName.toLowerCase() === "li";
      });
    }

    function closeSiblings(item) {
      var siblings = getSiblingItems(item);
      for (var i = 0; i < siblings.length; i += 1) {
        closeItem(siblings[i]);
      }
    }

    var expandableItems = menu.querySelectorAll("li");
    for (var i = 0; i < expandableItems.length; i += 1) {
      var item = expandableItems[i];
      var submenu = getDirectSubMenu(item);
      if (!submenu) {
        continue;
      }

      var link = getDirectLink(item);
      if (!link) {
        continue;
      }

      link.setAttribute("aria-haspopup", "true");
      link.setAttribute("aria-expanded", "false");
    }

    document.addEventListener("click", function(event) {
      var target = event.target;
      if (target && target.nodeType !== 1) {
        target = target.parentElement;
      }
      var link = target && target.closest ?
        target.closest("#dropmenu li > a") : null;

      if (!link || !menu.contains(link)) {
        closeAll();
        return;
      }

      var item = link.parentElement;
      var submenu = getDirectSubMenu(item);

      if (!submenu || !isPlaceholderLink(link)) {
        if (!shouldUseNativeHandler(link)) {
          event.preventDefault();
          window.location.assign(link.href);
        }
        return;
      }

      event.preventDefault();
      event.stopPropagation();

      var shouldOpen = !item.classList.contains("is-open");
      closeSiblings(item);

      if (shouldOpen) {
        item.classList.add("is-open");
        link.setAttribute("aria-expanded", "true");
      } else {
        closeItem(item);
      }
    }, true);

    document.addEventListener("keyup", function(event) {
      if (event.key === "Escape") {
        closeAll();
      }
    });

    if (mobileQuery.addEventListener) {
      mobileQuery.addEventListener("change", closeAll);
    } else {
      mobileQuery.addListener(closeAll);
    }
  });
})();
