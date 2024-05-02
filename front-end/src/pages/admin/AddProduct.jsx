import React, {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {AdminNavBar, Footer} from "../../components";
import axios from "axios";

const AddProduct = () => {
  const [formData, setFormData] = useState({
    productName: "",
    price: 0,
    brand: "",
    model: "",
    description: "",
    imgFile: null,
    quantityInStock: 0,
    color: "",
    storageCapacity: "",
    screenSize: "",
    ram: "",
    cpu: "",
    os: "",
    batteryCapacity: ""
  });

  const handleChange = (e) => {
    const {name, value} = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const uploadImg = (e) => {
    const file = e.target.files[0];
    setFormData((prevData) => ({
      ...prevData,
      imgFile: file
    }));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Gửi request để thêm mới sản phẩm
      console.log(formData);
      const response = await axios.post("http://localhost:8080/api/seller/product/new", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      
      // Xử lý kết quả thành công (nếu cần)
      console.log("Product added successfully:", response.data);

      // Redirect hoặc thực hiện các bước tiếp theo sau khi thêm sản phẩm
      window.alert("Thêm sản phẩm mới thành công");
      window.location.href = "/admin/product";
    } catch (error) {
      console.error("Error adding product:", error);
    }
  };

  return (
    <>
      <AdminNavBar/>
        <div className="container my-3 py-3">
          <h2 className="my-4">Thêm mới sản phẩm</h2>

          {/* Form thêm mới sản phẩm */}
          <form onSubmit={handleSubmit}>

            <div className="row">
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="brand" className="form-label">Thương hiệu</label>
                  <select
                      className="form-control"
                      id="brand"
                      name="brand"
                      value={formData.brand}
                      onChange={handleChange}
                      required
                  >
                    <option value="">Chọn thương hiệu</option>
                    <option value="1">Apple</option>
                    <option value="2">Samsung</option>
                    <option value="3">Xiaomi</option>
                    <option value="4">Oppo</option>
                    <option value="5">Khác</option>
                  </select>
                </div>
              </div>

              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="productName" className="form-label">Tên sản phẩm</label>
                  <input
                      type="text"
                      className="form-control"
                      id="productName"
                      name="productName"
                      value={formData.productName}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
            </div>

            <div className="row">
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="model" className="form-label">Model</label>
                  <input
                      type="text"
                      className="form-control"
                      id="model"
                      name="model"
                      value={formData.model}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="price" className="form-label">Giá</label>
                  <div className="input-group">
                    <span className="input-group-text">$</span>
                    <input
                        type="number"
                        className="form-control"
                        id="price"
                        name="price"
                        value={formData.price}
                        onChange={handleChange}
                        required
                    />
                  </div>
                </div>
              </div>
            </div>

            <div className="row">
              <div className="col-md-12">
                <div className="mb-3">
                  <label htmlFor="description" className="form-label">Mô tả sản phẩm</label>
                  <textarea
                      className="form-control"
                      id="description"
                      name="description"
                      value={formData.description}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
            </div>

            <h3>Cấu hình chi tiết</h3>

            <div className="row">
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="cpu" className="form-label">CPU</label>
                  <input
                      type="text"
                      className="form-control"
                      id="cpu"
                      name="cpu"
                      value={formData.cpu}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="ram" className="form-label">RAM</label>
                  <input
                      type="text"
                      className="form-control"
                      id="ram"
                      name="ram"
                      value={formData.ram}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
            </div>

            <div className="row">
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="storageCapacity" className="form-label">Bộ nhớ trong</label>
                  <input
                      type="text"
                      className="form-control"
                      id="storageCapacity"
                      name="storageCapacity"
                      value={formData.storageCapacity}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="screenSize" className="form-label">Màn hình</label>
                  <input
                      type="text"
                      className="form-control"
                      id="screenSize"
                      name="screenSize"
                      value={formData.screenSize}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
            </div>

            <div className="row">
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="os" className="form-label">Hệ điều hành</label>
                  <input
                      type="text"
                      className="form-control"
                      id="os"
                      name="os"
                      value={formData.os}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
              <div className="col-md-6">

                <div className="mb-3">
                  <label htmlFor="batteryCapacity" className="form-label">Dung lượng pin</label>
                  <input
                      type="text"
                      className="form-control"
                      id="batteryCapacity"
                      name="batteryCapacity"
                      value={formData.batteryCapacity}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
            </div>

            <div className="row">
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="color" className="form-label">Màu sắc</label>
                  <input
                      type="text"
                      className="form-control"
                      id="color"
                      name="color"
                      value={formData.color}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
              <div className="col-md-6">
                <div className="mb-3">
                  <label htmlFor="quantityInStock" className="form-label">Số lượng</label>
                  <input
                      type="number"
                      className="form-control"
                      id="quantityInStock"
                      name="quantityInStock"
                      value={formData.quantityInStock}
                      onChange={handleChange}
                      required
                  />
                </div>
              </div>
            </div>

            <div className="row">
                <div className="col-md-12">
                    <div className="mb-3">
                    <label htmlFor="imgFile" className="form-label">Ảnh sản phẩm</label>
                    <input
                        type="file"
                        className="form-control"
                        id="imgFile"
                        name="imgFile"
                        onChange={uploadImg}
                    />
                    </div>
                </div>
            </div>

            <button type="submit" className="btn btn-primary">Thêm sản phẩm</button>
            <Link to="/admin/product" className="btn btn-secondary ml-2">Quay lại</Link>
          </form>
        </div>
      <Footer/>
    </>
  );
};

export default AddProduct;
