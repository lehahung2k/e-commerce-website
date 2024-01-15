import React, { useState, useEffect } from "react";
import { useParams, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { Footer, Navbar } from "../components";

const OrderDetail = () => {
    const { orderId } = useParams();
    const [orderDetail, setOrderDetail] = useState(null);
    const location = useLocation();

    useEffect(() => {
        // Lấy dữ liệu từ location.state.products
        const productsInOrder = location.state?.products || [];
        setOrderDetail(productsInOrder);
        // Do something với dữ liệu productsInOrder

    }, [location.state]);

    return (
        <>
            <Navbar />
            <div className="container my-3 py-3">
                Chi tiết đơn hàng
            </div>
            <Footer />
        </>
    );
}

export default OrderDetail;
