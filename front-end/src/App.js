// App.js hoặc tệp cấu hình route chính
import React from 'react';
import {useSelector} from 'react-redux';
import {Route, Routes, Navigate, BrowserRouter} from 'react-router-dom';
import {
  Home,
  Products,
  ProductDetail,
  AboutPage,
  ContactPage,
  Cart,
  Checkout,
  Login,
  Register,
  PageNotFound,
  ManageUser,
  ManageProduct,
  AddProduct
} from './pages';

const App = () => {
    const userInfo = useSelector((state) => state.authReducer).userInfo;

    return (
        <Routes>
            {/* Cho tất cả các vai trò */}
            <Route path="/" element={<Home/>}/>
            <Route path="/product" element={<Products/>}/>
            <Route path="/product/:id" element={<ProductDetail/>}/>
            <Route path="/about" element={<AboutPage/>}/>
            <Route path="/contact" element={<ContactPage/>}/>
            <Route path="/cart" element={<Cart/>}/>
            <Route path="/checkout" element={<Checkout/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/register" element={<Register/>}/>
            <Route path="*" element={<PageNotFound/>}/>
            <Route path="/product/*" element={<PageNotFound/>}/>

            {/* Cho vai trò ADMIN */}
            {userInfo && userInfo.role === 'ADMIN' && (
                <>
                    <Route path="/admin/user" element={<ManageUser/>}/>
                    <Route path="/admin/product" element={<ManageProduct/>}/>
                    <Route path={"/admin/add-product"} element={<AddProduct/>}/>
                </>
            )}

            {/* Nếu không có vai trò nào khớp, chuyển hướng đến trang 404 */}
            <Route path="/*" element={<Navigate to="/404"/>}/>
        </Routes>
    );
};

export default App;
