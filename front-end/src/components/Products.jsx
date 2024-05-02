import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { addCart } from "../redux/action";

import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import axios from "axios";

import { Link } from "react-router-dom";

const Products = () => {
  const [data, setData] = useState({ content: [] });
  const [filter, setFilter] = useState(data.content); // Sử dụng data.content cho việc filter
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize] = useState(8);

  const authState = useSelector(state => state.authReducer);
  const isAuthenticated = authState && authState.isAuthenticated;

  const dispatch = useDispatch();

  const addProduct = async (product) => {
    try {
      // Gửi yêu cầu thêm vào giỏ hàng
      const response = await axios.post('http://localhost:8080/api/cart/add', {
        productId: product.productId,
        quantityInStock: 1, // Số lượng có thể điều chỉnh tùy vào ý của bạn
        }, {
        headers: {
          Authorization: `Bearer ${authState.token}`, // Bearer token (nếu có)
        },
      });
      console.log(response.data);
      alert('Thêm sản phẩm thành công. Hãy kiểm tra giỏ hàng!');
      // Nếu thành công, dispatch action để cập nhật giỏ hàng trong Redux
      if (response.data === true) {
        dispatch(addCart(product));
      }
    } catch (error) {
      console.error("Error during adding to cart:", error);
    }
  }

  useEffect(() => {
    const getProducts = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`http://localhost:8080/api/product?page=${currentPage}&size=${pageSize}`);
        console.log(response);
        setData(response.data.products);
        setFilter(response.data.products.content); // Thay đổi ở đây
        setLoading(false);
      } catch (error) {
        console.error("Error during fetching products:", error);
        setLoading(false);
      }
    };
    getProducts();
  }, [currentPage, pageSize]);

  const handleNextPage = () => {
    if (currentPage < data.totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const Loading = () => {
    return (
      <>
        <div className="col-12 py-5 text-center">
          <Skeleton height={40} width={560} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
      </>
    );
  };

  const filterByBrand = async (brand) => {
    const response = await axios.get(`http://localhost:8080/api/category/${brand}`);
    console.log(response.data.page.body);
    setFilter(response.data.page.body.products.content);
  }

  const ShowProducts = () => {
    return (
      <>
        <div className="buttons text-center py-5 col-12">
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => setFilter(data.content)}>Tất cả</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterByBrand(1)}>iPhone</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterByBrand(2)}>Samsung</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterByBrand(4)}>Oppo</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterByBrand(3)}>Xiaomi</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterByBrand(5)}>Khác</button>
        </div>

        {Array.isArray(filter) ? (
          filter.map((product) => {
            return (
              <div id={product.productId} key={product.productId} className="col-md-3 col-sm-6 col-xs-8 col-12 mb-4">
                <div className="card text-center h-100" key={product.productId}>
                  <img
                    className="card-img-top p-3"
                    src={product.fileName}
                    alt="Card"
                  />
                  <div className="card-body">
                    <h5 className="card-title">
                      {product.productName}
                    </h5>
                    <p className="card-text">
                      {product.description ? product.description.substring(0, 90) + '...' : ''}
                    </p>
                  </div>
                  <ul className="list-group list-group-flush">
                    <li className="list-group-item lead">$ {product.price}</li>
                    {/* <li className="list-group-item">Dapibus ac facilisis in</li>
                      <li className="list-group-item">Vestibulum at eros</li> */}
                  </ul>
                  <div className="card-body">
                    <Link to={"/product/" + product.productId} className="btn btn-dark m-1">
                      Xem ngay
                    </Link>
                    {isAuthenticated && product.productStatus === 0 ? (
                      <button className="btn btn-dark m-1" onClick={() => addProduct(product)}>
                        Thêm vào giỏ hàng
                      </button>
                      ) : (
                        <Link to="/login" className="btn btn-dark m-1">
                          Thêm vào giỏ hàng
                        </Link>
                      )
                    }
                  </div>
                </div>
              </div>

            );
          })
        ) : (
          <div className="col-12 py-5 text-center">
            <h3>Không có sản phẩm nào</h3>
          </div>
        )}
      </>
    );
  };
  return (
    <>
      <div className="container my-3 py-3">
        <div className="row">
          <div className="col-12">
            <h2 className="display-5 text-center">Sản phẩm của chúng tôi</h2>
            <hr />
          </div>
        </div>
        <div className="row justify-content-center">
          {loading ? <Loading /> : <ShowProducts />}
        </div>
        <div className="buttons text-center py-5 col-12">
          <button className="btn btn-outline-dark btn-sm m-2" onClick={handlePrevPage} disabled={currentPage === 1}>Trước</button>
          <span>{currentPage} / {data.totalPages}</span>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={handleNextPage} disabled={currentPage === data.totalPages}>Tiếp</button>
        </div>
      </div>
    </>
  );
};

export default Products;
