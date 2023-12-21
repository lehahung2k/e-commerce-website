import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';

import {
  Home,
  Product,
  Products,
  AboutPage,
  ContactPage,
  Cart,
  Login,
  Register,
  Checkout,
  PageNotFound,
  ManageUser
} from "./pages"
import {Provider, useSelector} from "react-redux";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import store from "./redux/store";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <BrowserRouter>
    <Provider store={store}>
      <App />
      {/*<Routes>*/}
      {/*  /!* for role USER *!/*/}
      {/*  <Route path="/" element={<Home />} />*/}
      {/*  <Route path="/product" element={<Products />} />*/}
      {/*  <Route path="/product/:id" element={<Product />} />*/}
      {/*  <Route path="/about" element={<AboutPage />} />*/}
      {/*  <Route path="/contact" element={<ContactPage />} />*/}
      {/*  <Route path="/cart" element={<Cart />} />*/}
      {/*  <Route path="/checkout" element={<Checkout />} />*/}
      {/*  /!* for ALL *!/*/}
      {/*  <Route path="/login" element={<Login />} />*/}
      {/*  <Route path="/register" element={<Register />} />*/}
      {/*  <Route path="*" element={<PageNotFound />} />*/}
      {/*  <Route path="/product/*" element={<PageNotFound />} />*/}
      {/*  /!* for role ADMIN *!/*/}
      {/*  <Route path="/admin" element={<ManageUser />} />*/}
      {/*</Routes>*/}
    </Provider>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
