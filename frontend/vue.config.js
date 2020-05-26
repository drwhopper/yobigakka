// https://developers.cyberagent.co.jp/blog/archives/25126/

const pages = {
  mainapp: {
    entry: "src/apps/mainapp/main.ts",
    template: "public/index.html",
    filename: "mainapp.html"
  },
  chatapp: {
    entry: "src/apps/chatapp/main.ts",
    template: "public/index.html",
    filename: "chatapp.html"
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
