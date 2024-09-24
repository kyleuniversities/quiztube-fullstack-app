module.exports = {
  apps: [
    {
      name: "quiztube-frontend-app",
      script: "npm start",
      error_file: "./pm2-error.log",
      out_file: "./pm2-out.log",
    },
  ],
};
