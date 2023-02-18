import qs from "qs";

export const googleSrc = `https://accounts.google.com/o/oauth2/v2/auth`;

const options = {
  client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID,
  redirect_uri: process.env.REACT_APP_REDIRECT_URL + "google",
  response_type: "token",
  scope: "profile email https://www.googleapis.com/auth/directory.readonly",
};

export const googleSrcWithClientId = googleSrc + "?" + qs.stringify(options);
