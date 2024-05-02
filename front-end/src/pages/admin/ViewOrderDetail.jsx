import React from "react";
import { AdminNavBar, Footer } from "../../components";
import { useEffect, useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

const ViewOrder = () => {
    const { orderId } = useParams();
    const authState = useSelector((state) => state.authReducer);
    const [product, setProduct] = useState([]);
    const [buyer, setBuyer] = useState([]);
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
                setBuyer(response.data.buyerName);
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

    const handleDeliver = async () => {
        const userConfirmed = window.confirm("Xác nhận vận chuyển đơn hàng này?");
        if (userConfirmed) {
            try {
                const response = await axios.patch(`http://localhost:8080/api/order/deliver/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${authState.token}`,
                        },
                    });
                window.location.href = "/admin/manage-order";
            } catch (error) {
                console.error("Error deleting order:", error);
            }
        }
    }

    const handleCancel = async () => {
        const userConfirmed = window.confirm("Xác nhận huỷ đơn hàng này?");
        if (userConfirmed) {
            try {
                await axios.patch(`http://localhost:8080/api/order/cancel/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${authState.token}`,
                        },
                    });
                window.location.href = "/admin/manage-order";
            } catch (error) {
                console.error("Error deleting order:", error);
            }
        }
    }

    return (
        <>
            <AdminNavBar />
            <div className="container my-3 py-3">
                <h3 className="text-center">Chi tiết đơn hàng</h3>
                <hr />
                <div className="row my-3 py-3">
                    <div className="col-md-6">
                        <strong>Tên khách hàng:</strong> {buyer}
                    </div>
                    <div className="col-md-6">
                        <strong>Mã đơn hàng: </strong> {orderId}
                    </div>
                </div>
                <div className="row my-3 py-3">
                    <div className="col-md-6">
                        <strong>Trạng thái đơn hàng:</strong> {orderStatusMap[orderStatus]}
                    </div>
                    <div className="col-md-6">
                        <strong>Tổng số tiền cần thanh toán:</strong> ${product.reduce((total, product) => total + product.price * product.count, 0)}
                    </div>
                </div>
                <table className="table">
                    <thead>
                        <tr>
                            <th>Mã sản phẩm</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Giá</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        {product.map((product) => (
                            <tr key={product.productId}>
                                <td>{product.productId}</td>
                                <td>{product.productName}</td>
                                <td>{product.count}</td>
                                <td>${product.price}</td>
                                <td>${product.price * product.count}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <hr />
                <div className="row">
                    {orderStatus === 0 ? (
                        <>
                            <button className="btn btn-warning btn-sm mx-2" onClick={handleDeliver}>Vận chuyển đơn hàng</button>
                            <button className="btn btn-danger btn-sm mx-2" onClick={handleCancel}>Huỷ đơn hàng</button>
                        </>
                    ) : (
                        <>
                            <button className="btn btn-warning btn-sm mx-2" disabled>Vận chuyển đơn hàng</button>
                            <button className="btn btn-danger btn-sm mx-2" disabled>Huỷ đơn hàng</button>
                        </>
                    )}
                </div>
            </div>
            <Footer />
        </>
    );
}

export default ViewOrder;
