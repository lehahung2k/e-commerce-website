import React, { useEffect, useState } from "react";
import Skeleton from "react-loading-skeleton";
import { Link, useParams } from "react-router-dom";
import Marquee from "react-fast-marquee";
import { useDispatch } from "react-redux";
import { addCart } from "../redux/action";
import axios from "axios";
import { useSelector } from "react-redux";

import { Footer, Navbar } from "../components";

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState([]);
  const [brand, setBrand] = useState([]); // [
  const [similarProducts, setSimilarProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loading2, setLoading2] = useState(false);
  const authState = useSelector(state => state.authReducer);

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
  };

  useEffect(() => {
    const getProduct = async () => {
      setLoading(true);
      setLoading2(true);
      try {
        const response = await axios.get(`http://localhost:8080/api/product/${id}`);
        const data = response.data.mobile;
        setProduct(data);
        setLoading(false);
        const response2 = await axios.get(`http://localhost:8080/api/category/${data.brand}`);
        const data2 = await response2.data;
        setBrand(data2);
        setSimilarProducts(data2.page.body.products.content);
        setLoading2(false);
      }
      catch (error) {
        console.error("Error during fetching product:", error);
        setLoading(false);
        setLoading2(false);
      }
    };
    getProduct();
  }, [id]);

  const Loading = () => {
    return (
      <>
        <div className="container my-5 py-2">
          <div className="row">
            <div className="col-md-6 py-3">
              <Skeleton height={400} width={400} />
            </div>
            <div className="col-md-6 py-5">
              <Skeleton height={30} width={250} />
              <Skeleton height={90} />
              <Skeleton height={40} width={70} />
              <Skeleton height={50} width={110} />
              <Skeleton height={120} />
              <Skeleton height={40} width={110} inline={true} />
              <Skeleton className="mx-3" height={40} width={110} />
            </div>
          </div>
        </div>
      </>
    );
  };

  const ShowProduct = () => {
    return (
      <>
        <div className="container my-5 py-2">
          <div className="row">
            <div className="col-md-6 col-sm-12 py-3">
              <img
                className="img-fluid"
                src={product.fileName}
                alt={product.productName}
                width="400px"
                height="400px"
              />
            </div>
            <div className="col-md-6 col-md-6 py-5">
              <h4 className="text-uppercase text-muted">Thương hiệu: {brand.brandName}</h4>
              <h1 className="display-5">{product.productName}</h1>
              {/** Thêm border để hiển thị cấu hình */}
              <p className="lead border"></p>
              <p className="lead border-light">
                <strong>Cấu hình:</strong>
              </p>
              <ul className="list-group list-group-flush">
                <li className="list-group-item lead">CPU: {product.cpu}</li>
                <li className="list-group-item lead">RAM: {product.ram}</li>
                <li className="list-group-item lead">ROM: {product.storageCapacity}</li>
                <li className="list-group-item lead">Màn hình: {product.screenSize}</li>
                <li className="list-group-item lead">Hệ điều hành: {product.os}</li>
                <li className="list-group-item lead">Pin: {product.batteryCapacity}</li>
                <li className="list-group-item lead">Màu sắc: {product.color}</li>
              </ul>
              <p className="lead border"></p>
              {product.productStatus === 0 ? (
                <p className="lead text-success">Còn hàng</p>
              ) : (
                <p className="lead text-danger">Hết hàng</p>
              )}
              <h3 className="display-6  my-4">Giá bán: ${product.price}</h3>
              <p className="lead">Chi tiết sản phẩm: {product.description}</p>
              {product.productStatus === 0 ? (
                <button
                  className="btn btn-dark mx-3"
                  onClick={() => addProduct(product)}
                >
                  Thêm vào giỏ hàng
                </button>
              ) : (
                <button className="btn btn-dark mx-3" disabled>
                  Thêm vào giỏ hàng
                </button>
              )}
              <Link to="/cart" className="btn btn-dark mx-3">
                Đi đến giỏ hàng
              </Link>
            </div>
          </div>
        </div>
      </>
    );
  };

  const Loading2 = () => {
    return (
      <>
        <div className="my-4 py-4">
          <div className="d-flex">
            <div className="mx-4">
              <Skeleton height={400} width={250} />
            </div>
            <div className="mx-4">
              <Skeleton height={400} width={250} />
            </div>
            <div className="mx-4">
              <Skeleton height={400} width={250} />
            </div>
            <div className="mx-4">
              <Skeleton height={400} width={250} />
            </div>
          </div>
        </div>
      </>
    );
  };

  const ShowSimilarProduct = () => {
    return (
      <>
        <div className="py-4 my-4">
          <div className="d-flex">
            {similarProducts.map((item) => {
              return (
                <div key={item.productId} className="card mx-4 text-center">
                  <img
                    className="card-img-top p-3"
                    src={item.fileName}
                    alt="Card"
                    height={300}
                    width={300}
                  />
                  <div className="card-body">
                    <h5 className="card-title">
                      {item.productName.substring(0, 15)}...
                    </h5>
                  </div>
                  {/* <ul className="list-group list-group-flush">
                    <li className="list-group-item lead">${product.price}</li>
                  </ul> */}
                  <div className="card-body">
                    <Link
                      to={"/product/" + item.productId}
                      className="btn btn-dark m-1"
                    >
                      Xem ngay
                    </Link>
                    <button
                      className="btn btn-dark m-1"
                      onClick={() => addProduct(item)}
                    >
                      Thêm vào giỏ hàng
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </>
    );
  };
  return (
    <>
      <Navbar />
      <div className="container">
        <div className="row">{loading ? <Loading /> : <ShowProduct />}</div>
        <div className="row my-5 py-5">
          <div className="d-none d-md-block">
            <h2 className="">Sản phẩm tương tự</h2>
            <Marquee
              pauseOnHover={true}
              pauseOnClick={true}
              speed={50}
            >
              {loading2 ? <Loading2 /> : <ShowSimilarProduct />}
            </Marquee>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default ProductDetail;
