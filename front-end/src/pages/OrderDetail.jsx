import React, { useState, useEffect } from "react";
import { useParams, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
import { Footer, Navbar } from "../components";
import axios from "axios";

const OrderDetail = () => {
    const { orderId } = useParams();
    const authState = useSelector((state) => state.authReducer);
    const [product, setProduct] = useState([]);
    const [orderStatus, setOrderStatus] = useState(0);


    useEffect(() => {
        const fetchOrder = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/order/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${authState.token}`,
                        },
                    });
                setProduct(response.data.products);
                setOrderStatus(response.data.orderStatus);
            } catch (error) {
                console.error("Error fetching order:", error);
            }
        };

        fetchOrder();
    }, []);

    const orderStatusMap = {
        0: "Đã đặt",
        1: "Đã hoàn thành",
        2: "Huỷ đơn hàng",
        3: "Đang vận chuyển",
    };

    const handleDelete = async () => {
        const userConfirmed = window.confirm("Bạn có chắc chắn muốn huỷ đơn hàng này?");
        if (userConfirmed) {
            try {
                const response = await axios.patch(`http://localhost:8080/api/order/cancel/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${authState.token}`,
                        },
                    });
                window.location.href = "/order";
            } catch (error) {
                console.error("Error deleting order:", error);
            }
        }
    }

    const handleFinish = async () => {
        const userConfirmed = window.confirm("Bạn đã nhận được đơn hàng này?");
        if (userConfirmed) {
            try {
                const response = await axios.patch(`http://localhost:8080/api/order/finish/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${authState.token}`,
                        },
                    });
                window.location.href = "/order";
            } catch (error) {
                console.error("Error finishing order:", error);
            }
        }
    }

    return (
        <>
            <Navbar />
            <div className="container my-3 py-3">
                <h3 className="my-3 py-3">Chi tiết đơn hàng</h3>
                <hr />
                <div className="row my-3 py-3">
                    <div className="col-md-4">
                        <strong>Mã đơn hàng: </strong> {orderId}
                    </div>
                    <div className="col-md-4">
                        <strong>Tổng số tiền cần thanh toán:</strong> ${product.reduce((total, product) => total + product.price * product.count, 0)}
                    </div>
                    <div className="col-md-4">
                        <strong>Trạng thái đơn hàng:</strong> {orderStatusMap[orderStatus]}
                    </div>
                </div>
                <table className="table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        {product.map((product, index) => (
                            <tr key={product.productId}>
                                <td>{index + 1}</td>
                                <td>{product.productName}</td>
                                <td>${product.price}</td>
                                <td>{product.count}</td>
                                <td>${product.price * product.count}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <hr />
                <div className="row">
                    {orderStatus === 3 ? (
                        <button className="btn btn-success btn-sm mx-2" onClick={handleFinish}>Đã nhận được hàng</button>
                    ) : (
                        <button className="btn btn-success btn-sm mx-2" disabled>Đã nhận được hàng</button>
                    )}
                    {orderStatus !== 0 ? (
                        <>
                            <button className="btn btn-danger btn-sm mx-2" disabled>Huỷ đơn hàng</button>
                        </>
                    ): (
                        <>
                            <button className="btn btn-danger btn-sm mx-2" onClick={handleDelete}>Huỷ đơn hàng</button>
                        </>
                    )}
                </div>
            </div>
            <Footer />
        </>
    );
}

export default OrderDetail;
