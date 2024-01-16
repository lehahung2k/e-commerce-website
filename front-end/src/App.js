// App.js hoặc tệp cấu hình route chính
import React from 'react';
import { useSelector } from 'react-redux';
import { Route, Routes, Navigate, BrowserRouter } from 'react-router-dom';
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
    AddProduct,
    EditProduct,
    ManageOrder,
    Account,
    Setting,
    Order,
    OrderDetail,
    ManageOrderDetail
} from './pages';

const App = () => {
    const userInfo = useSelector((state) => state.authReducer).userInfo;

    return (
        <Routes>
            {/* Cho tất cả các vai trò */}
            <Route path="/" element={<Home />} />
            <Route path="/product" element={<Products />} />
            <Route path="/product/:id" element={<ProductDetail />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/contact" element={<ContactPage />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/checkout" element={<Checkout />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="*" element={<PageNotFound />} />
            <Route path="/product/*" element={<PageNotFound />} />
            <Route path={"/account"} element={<Account />} />
            <Route path={"/settings"} element={<Setting />} />

            {/* Cho vai trò USER */}
            {userInfo && userInfo.role === 'USER' && (
                <>
                    <Route path='/order' element={<Order />} />
                    <Route path="/order/:orderId" element={<OrderDetail />} />
                </>
            )}

            {/* Cho vai trò ADMIN */}
            {userInfo && userInfo.role === 'ADMIN' && (
                <>
                    <Route path="/admin/user" element={<ManageUser />} />
                    <Route path="/admin/product" element={<ManageProduct />} />
                    <Route path={"/admin/add-product"} element={<AddProduct />} />
                    <Route path={"/admin/edit-product/:id"} element={<EditProduct />} />
                    <Route path={"/admin/manage-order"} element={<ManageOrder />} />
                    <Route path={"/admin/manage-order/:orderId"} element={<ManageOrderDetail />} />
                </>
            )}

            {/* Nếu không có vai trò nào khớp, chuyển hướng đến trang 404 */}
            <Route path="/*" element={<Navigate to="/404" />} />
        </Routes>
    );
};

export default App;
