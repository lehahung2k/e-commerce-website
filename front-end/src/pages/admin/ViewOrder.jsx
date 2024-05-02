import React from "react";
import {AdminNavBar, Footer} from "../../components";
import {useEffect, useState} from "react";
import axios from "axios";
import {useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {Link} from "react-router-dom";

import '../../assets/style.css'

const ViewOrder = () => {
    const [orders, setOrders] = useState([]);
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
        <>
            <AdminNavBar/>
            <div className="container my-3 py-3">
                <h1 className="text-center">Danh sách đơn hàng</h1>
                <hr/>
                <table className="table">
                    <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Khách hàng</th>
                        <th>Email</th>
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
                            <td>{order.buyerName}</td>
                            <td>{order.buyerEmail}</td>
                            <td>{order.buyerPhone}</td>
                            <td>{order.buyerAddress}</td>
                            <td>${order.orderAmount}</td>
                            <td className={`order-status-${order.orderStatus}`}>{orderStatusMap[order.orderStatus]}</td>
                            <td>{new Date(order.createTime).toLocaleString()}</td>
                            <td>
                                <Link to={{
                                    pathname: `/admin/manage-order/${order.orderId}`
                                }} className="btn btn-outline-dark">
                                Xem đơn
                                </Link>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>   
            </div>
            <Footer/>
        </>
    );
}

export default ViewOrder;
