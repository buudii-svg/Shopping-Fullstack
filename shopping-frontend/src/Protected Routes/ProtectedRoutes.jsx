import React, { Component } from "react";
import { Redirect, Route } from "react-router-dom";
import jwtDecode from "jwt-decode";

export default class ProtectedRoutes extends Component {
  render() {
    console.log(this.props.path);
    let token = localStorage.getItem("token");
    try {
      let data = jwtDecode(token);
      if (token) {
        if (data.role === "Client") {
          if (
            this.props.path === "/seller/register" ||
            this.props.path === "/seller/login" ||
            this.props.path === "/seller/view/sold/products" ||
            this.props.path === "/seller/view/products" ||
            this.props.path === "/seller/add/product" ||
            this.props.path === "/admin/add/company" ||
            this.props.path === "/admin/cust/accounts" ||
            this.props.path === "/admin/selling/accounts" ||
            this.props.path === "/admin/shipping/companies" ||
            this.props.path === "/seller/register"
          ) {
            return <Redirect to="/unauthorized" />;
          } else {
            return (
              <Route path={this.props.path} component={this.props.component} />
            );
          }
        } else if (data.role === "selling") {
          if (
            this.props.path === "/admin/add/company" ||
            this.props.path === "/admin/cust/accounts" ||
            this.props.path === "/admin/selling/accounts" ||
            this.props.path === "/admin/shipping/companies" ||
            this.props.path === "/seller/register" ||
            this.props.path === "/view/current/orders" ||
            this.props.path === "/view/previous/orders" ||
            this.props.path === "/view/cart"
          ) {
            return <Redirect to="/unauthorized" />;
          } else {
            return (
              <Route path={this.props.path} component={this.props.component} />
            );
          }
        } else if (data.role === "Admin") {
          if (
            this.props.path === "/view/current/orders" ||
            this.props.path === "/view/previous/orders" ||
            this.props.path === "/view/cart" ||
            this.props.path === "/seller/view/sold/products" ||
            this.props.path === "/seller/view/products" ||
            this.props.path === "/seller/add/product"
          ) {
            return <Redirect to="/unauthorized" />;
          } else {
            return (
              <Route path={this.props.path} component={this.props.component} />
            );
          }
        }
      } else {
        return <Redirect to="/" />;
      }
    } catch (e) {
      return <Redirect to="/" />;
    }
  }
}
