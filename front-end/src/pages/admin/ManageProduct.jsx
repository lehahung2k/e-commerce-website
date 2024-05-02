import React, { useEffect, useState } from "react";
import { AdminNavBar, Footer } from "../../components";
import { Link } from "react-router-dom";
import axios from "axios";

const ManageUser = () => {

  const [data, setData] = useState([]);
  const [products, setProducts] = useState(data.content);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize] = useState(8);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const getProducts = async () => {
      try {
        let apiUrl = 'http://localhost:8080/api/product';
        if (searchTerm) {
          apiUrl += `/search?keyword=${searchTerm}`;
        } else {
          apiUrl += `?page=${currentPage}&size=${pageSize}`;
        }
        const response = await axios.get(apiUrl);
        setData(response.data.products);
        setProducts(response.data.products.content);
      } catch (error) {
        console.error("Error during fetching products:", error);
      }
    };
    getProducts();
  }, [currentPage, pageSize, searchTerm]);

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

  // const handleSearch = async () => {
  //   try {
  //     const response =
  //         await axios.get(`http://localhost:8080/api/product/search?keyword=${searchTerm}&page=${currentPage}&size=${pageSize}`);
  //     setData(response.data);
  //     setProducts(response.data.content);
  //   } catch (error) {
  //     console.error("Error during searching products:", error);
  //   }
  // };

  const handleDeleteProduct = async (productId) => {
    const userConfirmed = window.confirm("Bạn có chắc chắn muốn xoá sản phẩm này?");
    if (userConfirmed) {
      try {
        const response = await axios.delete(`http://localhost:8080/api/seller/product/${productId}/delete`);
        console.log("Product deleted successfully:", response.data);

        // Cập nhật danh sách sản phẩm sau khi xoá
        const updatedProducts = products.filter((product) => product.productId !== productId);
        setProducts(updatedProducts);
      } catch (error) {
        console.error("Error deleting product:", error);
      }
    }
  };

  return (
    <>
      <AdminNavBar />
      <div className="container">
        <h2 className="my-4">Quản lý Sản phẩm</h2>

        <div className={'row'}>
          <div className={'col-6'}>
            <Link to="/admin/add-product" className="btn btn-outline-dark">
              <i className="fa-solid fa-plus"></i> Thêm sản phẩm
            </Link>
          </div>
          <div className="input-group mb-3 col-6">
            <input
                type="text"
                className="form-control"
                placeholder="Tìm kiếm sản phẩm..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <div className="input-group-append">
              <button className="btn btn-outline-primary" type="button">
                <i className="fa-solid fa-magnifying-glass"></i>
              </button>
            </div>
          </div>
        </div>

        {/* Bảng hiển thị danh sách sản phẩm */}
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tên sản phẩm</th>
              <th>Giá</th>
              <th>Số lượng</th>
              <th>Màu sắc</th>
              <th>Trạng thái</th>
              {/* Thêm các cột khác cần hiển thị */}
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {Array.isArray(products) ? (
              products.map((product, index) => {
                return (
                  <tr key={product.productId}>
                    <td>{index + 1}</td>
                    <td>{product.productName}</td>
                    <td>${product.price}</td>
                    <td>{product.quantityInStock}</td>
                    <td>{product.color}</td>
                    <td>
                      {product.productStatus === 0 ? (
                        <span className="text-success">Còn hàng</span>
                      ) : (
                        <span className="text-danger">Hết hàng</span>
                      )}
                    </td>
                    {/* Thêm các cột khác tương ứng */}
                    <td>
                      {/* Nút để xem chi tiết hoặc chỉnh sửa sản phẩm */}
                      <Link to={`/admin/edit-product/${product.productId}`} className="btn btn-warning btn-sm">
                        Chỉnh sửa
                      </Link>
                      {/* Nút để xóa sản phẩm */}
                      <button
                        className="btn btn-danger btn-sm ml-2"
                        onClick={() => handleDeleteProduct(product.productId)}
                      >
                        Xóa
                      </button>
                    </td>
                  </tr>
                );
              }
              )) : (
              <tr>
                <td colSpan="4" className="text-center">
                  Không có sản phẩm nào
                </td>
              </tr>
            )
            }
          </tbody>
        </table>
      </div>
      <div className="buttons text-center py-5 col-12">
        <button className="btn btn-outline-dark btn-sm m-2" onClick={handlePrevPage} disabled={currentPage === 1}>Trước</button>
        <span>{currentPage} / {data.totalPages}</span>
        <button className="btn btn-outline-dark btn-sm m-2" onClick={handleNextPage} disabled={currentPage === data.totalPages}>Tiếp</button>
      </div>
      <Footer />
    </>
  );
}

export default ManageUser;
