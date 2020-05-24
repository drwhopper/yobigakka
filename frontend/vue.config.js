// https://developers.cyberagent.co.jp/blog/archives/25126/

const pages = {
  app1: {
    entry: "src/pages/app1/main.ts",
    template: "public/index.html",
    filename: "app1.html"
  },
  appR: {
    entry: "src/main.ts",
    template: "public/index.html",
    filename: "app.html"
  }
};

module.exports = {
  pages: pages,
  outputDir: "../public/vuejs",
  publicPath: "/assets/vuejs",
  filenameHashing: false,

  chainWebpack: config => {
    Object.keys(pages).forEach(page => {
      config.plugins.delete(`html-${page}`);
      config.plugins.delete(`preload-${page}`);
      config.plugins.delete(`prefetch-${page}`);
    });
  }
};
