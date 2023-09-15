import React from "react";
import { Route } from "react-router-dom";

const UnauthorizedRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={(props) => (
      <React.Fragment>
        <Component {...props} />
      </React.Fragment>
    )}
  />
);

export default UnauthorizedRoute;
