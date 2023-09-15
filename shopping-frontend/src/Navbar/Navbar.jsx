import jwtDecode from "jwt-decode";
import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import { useState, useEffect } from "react";
export default function Navbar() {
  const [tokenData, setTokenData] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setTokenData(decodedToken);
    }
  }, []);
  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container-fluid">
          <NavLink className="navbar-brand" to="/">
            Shopping
          </NavLink>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <NavLink className="nav-link" to="/">
                  Home
                </NavLink>
              </li>
              {tokenData?.role === "Admin" ? (
                <>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/seller/register">
                      Add representative account
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/admin/add/company">
                      Add shipping company
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/admin/cust/accounts">
                      Customers Accounts
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/admin/selling/accounts">
                      Representative accounts
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink
                      className="nav-link"
                      to="/admin/shipping/companies"
                    >
                      Shipping companies
                    </NavLink>
                  </li>
                </>
              ) : tokenData?.role === "selling" ? (
                <>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/seller/add/product">
                      Add product
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/seller/view/products">
                      View offered products
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink
                      className="nav-link"
                      to="/seller/view/sold/products"
                    >
                      View sold products
                    </NavLink>
                  </li>
                </>
              ) : tokenData?.role === "Client" ? (
                <>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/view/current/orders">
                      View current orders
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/view/previous/orders">
                      View previous orders
                    </NavLink>
                  </li>
                  <li className="nav-item">
                    <NavLink className="nav-link" to="/view/cart">
                      View cart
                    </NavLink>
                  </li>
                </>
              ) : null}
            </ul>
            {tokenData ? (
              <div>
                <button
                  className="btn btn-outline-secondary ms-5 me-1 shadow-none border-0 text-white"
                  onClick={() => {
                    localStorage.removeItem("token");
                    window.location.href = "/";
                  }}
                >
                  Logout
                </button>
              </div>
            ) : (
              <div>
                <NavLink
                  className="btn btn-outline-secondary ms-5 me-1 shadow-none border-0 text-white"
                  to="/login"
                >
                  Sign in
                </NavLink>
              </div>
            )}
          </div>
        </div>
      </nav>
    </>
  );
}
