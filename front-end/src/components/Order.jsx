import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

import '../assets/style.css'

const Order = () => {
    const [orders, setOrders] = useState([]);
    const { orderId } = useParams();
    const authState = useSelector((state) => state.authReducer);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/order",
                    {
                        headers: {
                            Authorization: `Bearer ${authState.token}`,
                        },
                    });
                setOrders(response.data.orders.content);
            } catch (error) {
                console.error("Error fetching orders:", error);
            }
        };

        fetchOrders();
    }, []);

    const orderStatusMap = {
        0: "Đã đặt",
        1: "Đã hoàn thành",
        2: "Huỷ đơn hàng",
        3: "Đang vận chuyển",
    };

    return (
        <div className="container my-3 py-3">
            <h2>Danh sách đơn hàng</h2>
            <hr/>
            <table className="table">
                <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Số điện thoại</th>
                        <th>Địa chỉ nhận</th>
                        <th>Số tiền</th>
                        <th>Trạng thái đơn</th>
                        <th>Ngày đặt</th>
                        <th>Xem đơn</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.map((order) => (
                        <tr key={order.orderId}>
                            <td>{order.orderId}</td>
                            <td>{order.buyerPhone}</td>
                            <td>{order.buyerAddress}</td>
                            <td>${order.orderAmount}</td>
                            <td className={`order-status-${order.orderStatus}`}>{orderStatusMap[order.orderStatus]}</td>
                            <td>{new Date(order.createTime).toLocaleString()}</td>
                            <td>
                                <Link to={{
                                    pathname: `/order/${order.orderId}`
                                }} className="btn btn-warning btn-sm">
                                    Xem
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Order;
