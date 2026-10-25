import React, { useState, useEffect } from "react";
import { Footer, Navbar } from "../components";
import { useSelector, useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import API_BASE_URL from '../config';

const Checkout = () => {
  const state = useSelector((state) => state.handleCart);
  const [cartInfo, setCartInfo] = useState([]);
  const authState = useSelector((state) => state.authReducer);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // Shipping form state
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    phone: "",
    address: "",
    address2: "",
    country: "Vietnam",
    city: "",
    zip: "",
    paymentMethod: "COD",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleFormChange = (e) => {
    setForm({ ...form, [e.target.id]: e.target.value });
  };

  useEffect(() => {
    const getCart = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/cart`, {
          headers: { Authorization: `Bearer ${authState.token}` },
        });
        setCartInfo(response.data.products || []);
      } catch (error) {
        console.error("Error during fetching cart:", error);
      }
    };
    getCart();
  }, [authState.token]);

  const handleCheckout = async (e) => {
    e.preventDefault();
    setError("");

    // Basic validation
    if (!form.firstName || !form.lastName || !form.phone || !form.address || !form.city) {
      setError("Vui lòng điền đầy đủ thông tin giao hàng.");
      return;
    }

    try {
      setLoading(true);
      await axios.post(
        `${API_BASE_URL}/cart/checkout`,
        form,
        { headers: { Authorization: `Bearer ${authState.token}` } }
      );
      dispatch({ type: "CLEAR_CART" });
      navigate("/order");
    } catch (err) {
      setError("Đặt hàng thất bại. Vui lòng thử lại.");
      console.error("Checkout error:", err);
    } finally {
      setLoading(false);
    }
  };

  const EmptyCart = () => (
    <div className="container">
      <div className="row">
        <div className="col-md-12 py-5 bg-light text-center">
          <h4 className="p-3 display-5">Giỏ hàng trống</h4>
          <Link to="/" className="btn btn-outline-dark mx-4">
            <i className="fa fa-arrow-left"></i> Tiếp tục mua sắm
          </Link>
        </div>
      </div>
    </div>
  );

  const ShowCheckout = () => {
    let subtotal = 0;
    const shipping = 30.0;
    let totalItems = 0;
    cartInfo.forEach((item) => {
      subtotal += item.price * item.count;
      totalItems += item.count;
    });

    return (
      <>
        <div className="container py-5">
          <div className="row my-4">
            {/* Order Summary */}
            <div className="col-md-5 col-lg-4 order-md-last">
              <div className="card mb-4">
                <div className="card-header py-3 bg-light">
                  <h5 className="mb-0">Thông tin thanh toán</h5>
                </div>
                <div className="card-body">
                  <ul className="list-group list-group-flush">
                    <li className="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
                      Sản phẩm ({totalItems})<span>${Math.round(subtotal)}</span>
                    </li>
                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                      Phí ship <span>${shipping}</span>
                    </li>
                    <li className="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                      <strong>Tổng</strong>
                      <strong>${Math.round(subtotal + shipping)}</strong>
                    </li>
                  </ul>
                </div>
              </div>
            </div>

            {/* Shipping & Payment Form */}
            <div className="col-md-7 col-lg-8">
              {error && <div className="alert alert-danger">{error}</div>}
              <div className="card mb-4">
                <div className="card-header py-3">
                  <h4 className="mb-0">Địa chỉ giao hàng</h4>
                </div>
                <div className="card-body">
                  <form onSubmit={handleCheckout}>
                    <div className="row g-3">
                      <div className="col-sm-6 my-1">
                        <label htmlFor="firstName" className="form-label">Họ</label>
                        <input type="text" className="form-control" id="firstName"
                          value={form.firstName} onChange={handleFormChange} required />
                      </div>
                      <div className="col-sm-6 my-1">
                        <label htmlFor="lastName" className="form-label">Tên đệm và tên</label>
                        <input type="text" className="form-control" id="lastName"
                          value={form.lastName} onChange={handleFormChange} required />
                      </div>
                      <div className="col-12 my-1">
                        <label htmlFor="phone" className="form-label">Số điện thoại</label>
                        <input type="tel" className="form-control" id="phone"
                          value={form.phone} onChange={handleFormChange} required />
                      </div>
                      <div className="col-12 my-1">
                        <label htmlFor="address" className="form-label">Địa chỉ</label>
                        <input type="text" className="form-control" id="address"
                          placeholder="Số nhà, tên đường" value={form.address}
                          onChange={handleFormChange} required />
                      </div>
                      <div className="col-12">
                        <label htmlFor="address2" className="form-label">
                          Địa chỉ 2 <span className="text-muted">(Không bắt buộc)</span>
                        </label>
                        <input type="text" className="form-control" id="address2"
                          placeholder="Tòa nhà, tầng, phòng..." value={form.address2}
                          onChange={handleFormChange} />
                      </div>
                      <div className="col-md-5 my-1">
                        <label htmlFor="country" className="form-label">Nước</label>
                        <select className="form-select" id="country"
                          value={form.country} onChange={handleFormChange} required>
                          <option value="Vietnam">Vietnam</option>
                          <option value="Thailand">Thailand</option>
                          <option value="Laos">Laos</option>
                          <option value="Campuchia">Campuchia</option>
                        </select>
                      </div>
                      <div className="col-md-4 my-1">
                        <label htmlFor="city" className="form-label">Thành phố</label>
                        <input type="text" className="form-control" id="city"
                          value={form.city} onChange={handleFormChange} required />
                      </div>
                      <div className="col-md-3 my-1">
                        <label htmlFor="zip" className="form-label">Zip</label>
                        <input type="text" className="form-control" id="zip"
                          value={form.zip} onChange={handleFormChange} />
                      </div>
                    </div>

                    <hr className="my-4" />
                    <h4 className="mb-3">Phương thức thanh toán</h4>
                    <div className="row g-3">
                      <div className="col-12">
                        <div className="form-check">
                          <input className="form-check-input" type="radio" id="cod"
                            name="paymentMethod" value="COD"
                            checked={form.paymentMethod === "COD"}
                            onChange={(e) => setForm({ ...form, paymentMethod: e.target.value })} />
                          <label className="form-check-label" htmlFor="cod">
                            💵 Thanh toán khi nhận hàng (COD)
                          </label>
                        </div>
                      </div>
                    </div>

                    <hr className="my-4" />
                    <button type="submit" className="w-100 btn btn-dark btn-lg" disabled={loading}>
                      {loading ? "Đang xử lý..." : "Đặt hàng"}
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  };

  return (
    <>
      <Navbar />
      <div className="container my-3 py-3">
        <h1 className="text-center">Checkout</h1>
        <hr />
        {cartInfo.length > 0 ? <ShowCheckout /> : <EmptyCart />}
      </div>
      <Footer />
    </>
  );
};

export default Checkout;
