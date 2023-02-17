import qs from "qs";

export const githubSrc = `https://github.com/login/oauth/authorize`;

const options = {
  client_id: process.env.REACT_APP_GITHUB_CLIENT_ID,
  redirect_uri: process.env.REACT_APP_REDIRECT_URL + "github",
};

export const githubSrcWithClientId = githubSrc + "?" + qs.stringify(options);
