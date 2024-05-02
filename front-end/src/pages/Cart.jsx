import React, { useState, useEffect } from "react";
import { Footer, Navbar } from "../components";
import { useSelector, useDispatch } from "react-redux";
import { addCart, delCart, login } from "../redux/action";
import { Link } from "react-router-dom";
import axios from "axios";

const Cart = () => {
  const [productInOrders, setProductInOrders] = useState([]);
  const [total, setTotal] = useState(0);
  const authState = useSelector((state) => state.authReducer);
  const dispatch = useDispatch();

  const isAuthenticated = authState && authState.isAuthenticated;

  useEffect(() => {
    // Load cart data
    const getCart = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/cart`,
          {
            headers: {
              Authorization: `Bearer ${authState.token}`,
            },
          }
        );
        dispatch({ type: "SET_CART", payload: response.data.products.length });
        setProductInOrders(response.data.products);
        setTotal(response.data.total);
      } catch (error) {
        console.error("Error during fetching cart:", error);
      }
    };

    getCart();
  }, []);

  const EmptyCart = () => {
    return (
      <>
        {isAuthenticated ? (
          <div className="container">
            <div className="row">
              <div className="col-md-12 py-5 bg-light text-center">
                <h4 className="p-3 display-5">Giỏ hàng rỗng</h4>
                <Link to="/" className="btn  btn-outline-dark mx-4">
                  <i className="fa fa-arrow-left"></i> Tiếp tục mua sắm
                </Link>
              </div>
            </div>
          </div>
        ) : (
          <div className="container">
            {/** redirect to login */}
            <div className="row">
              <div className="col-md-12 py-5 bg-light text-center">
                <h4 className="p-3 display-5">Đăng nhập để xem giỏ hàng</h4>
                <Link to="/login" className="btn  btn-outline-dark mx-4">
                  <i className="fa fa-arrow-left"></i> Đăng nhập
                </Link>
              </div>
            </div>
          </div>
        )
        }
      </>
    );
  };

  const increaseQuantity = async (product) => {
    const updatedProduct = product.count + 1;
    try {
      const response = await axios.put(
        `http://localhost:8080/api/cart/${product.productId}`,
        updatedProduct,
        {
          headers: {
            Authorization: `Bearer ${authState.token}`,
            'Content-Type': 'application/json', 
          },
        }
      );
      dispatch(addCart(updatedProduct));
      setProductInOrders((prevProducts) =>
      prevProducts.map((prevProduct) =>
        prevProduct.productId === product.productId
          ? { ...prevProduct, count: updatedProduct }
          : prevProduct
        )
      );
      setTotal(response.data.total);
    } catch (error) {
      console.error("Error during updating cart:", error);
    }
  };

  const decreaseQuantity = async (product) => {
    if (product.count > 1) {
      const updatedProduct = product.count - 1;
      try {
        const response = await axios.put(
          `http://localhost:8080/api/cart/${product.productId}`,
          updatedProduct,
          {
            headers: {
              Authorization: `Bearer ${authState.token}`,
              'Content-Type': 'application/json', 
            },
          }
        );
        dispatch(delCart(updatedProduct));
        setTotal(response.data.total);
      } catch (error) {
        console.error("Error during updating cart:", error);
      }
    }
  };

  const ShowCart = () => {
    let subtotal = 0;
    let shipping = 30.0;
    let totalItems = 0;
    productInOrders.map((item) => {
      return (subtotal += item.price * item.count);
    });

    productInOrders.map((item) => {
      return (totalItems += item.count);
    });
    return (
      <>
        <section className="h-100 gradient-custom">
          <div className="container py-5">
            <div className="row d-flex justify-content-center my-4">
              <div className="col-md-8">
                <div className="card mb-4">
                  <div className="card-header py-3">
                    <h5 className="mb-0">Danh sách sản phẩm</h5>
                  </div>
                  <div className="card-body">
                    {productInOrders.map((item) => {
                      return (
                        <div key={item.productId}>
                          <div className="row d-flex align-items-center">
                            <div className="col-lg-3 col-md-12">
                              <div className="bg-image rounded" data-mdb-ripple-color="light">
                                <img
                                  src={item.fileName} alt={item.productName} width={100}
                                />
                              </div>
                            </div>

                            <div className="col-lg-5 col-md-6">
                              <Link to={`/product/${item.productId}`}>
                                <strong>{item.productName}</strong>
                              </Link>
                            </div>

                            <div className="col-lg-4 col-md-6">
                              <div className="d-flex mb-4" style={{ maxWidth: "300px" }}>
                                <button
                                  className="btn px-3"
                                  onClick={() => {
                                    decreaseQuantity(item);
                                  }}
                                >
                                  <i className="fas fa-minus"></i>
                                </button>

                                <p className="mx-5">{item.count}</p>

                                <button
                                  className="btn px-3"
                                  onClick={() => {
                                    increaseQuantity(item);
                                  }}
                                >
                                  <i className="fas fa-plus"></i>
                                </button>
                              </div>

                              <p className="text-start text-md-center">
                                <strong>
                                  <span className="text-muted">{item.count}</span>{" "}
                                  x ${item.price}
                                </strong>
                              </p>
                            </div>
                          </div>

                          <hr className="my-4" />
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>
              <div className="col-md-4">
                <div className="card mb-4">
                  <div className="card-header py-3 bg-light">
                    <h5 className="mb-0">Thanh toán</h5>
                  </div>
                  <div className="card-body">
                    <ul className="list-group list-group-flush">
                      <li className="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
                        Sản phẩm: ({totalItems})<span>${Math.round(subtotal)}</span>
                      </li>
                      <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                        Phí ship:
                        <span>${shipping}</span>
                      </li>
                      <li className="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                        <div>
                          <strong>Tổng: </strong>
                        </div>
                        <span>
                          <strong>${Math.round(subtotal + shipping)}</strong>
                        </span>
                      </li>
                    </ul>

                    <Link
                      to="/checkout"
                      className="btn btn-dark btn-lg btn-block"
                    >
                      Đến trang thanh toán
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </>
    );
  };

  return (
    <>
      <Navbar />
      <div className="container my-3 py-3">
        <h1 className="text-center">Giỏ hàng</h1>
        <hr />
        {productInOrders.length > 0 ? <ShowCart /> : <EmptyCart />}
      </div>
      <Footer />
    </>
  );
};

export default Cart;
