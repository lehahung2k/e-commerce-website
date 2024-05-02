import React, { useEffect, useState } from 'react';
import { AdminNavBar, Footer } from '../../components';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';

const EditProduct = () => {
    const { id } = useParams();
    const [product, setProduct] = useState({});
    const [formData, setFormData] = useState({
        productName: "",
        price: 0,
        brand: "",
        model: "",
        description: "",
        fileName: "",
        quantityInStock: 0,
        color: "",
        storageCapacity: "",
        screenSize: "",
        ram: "",
        cpu: "",
        os: "",
        batteryCapacity: "",
        productStatus: 0
    });

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/product/${id}`);
                const data = response.data.mobile;
                setProduct(data);
                setFormData({
                    ...formData,
                    productName: data.productName,
                    price: data.price,
                    brand: data.brand,
                    model: data.model,
                    description: data.description,
                    fileName: data.fileName,
                    quantityInStock: data.quantityInStock,
                    color: data.color,
                    storageCapacity: data.storageCapacity,
                    screenSize: data.screenSize,
                    ram: data.ram,
                    cpu: data.cpu,
                    os: data.os,
                    batteryCapacity: data.batteryCapacity,
                    productStatus: data.productStatus
                });
            } catch (error) {
                console.error("Error during fetching product details:", error);
            }
        };
        fetchProduct();
    }, [id]);

    const handleUpdate = async () => {
        try {
            // Tạo đối tượng mới để gửi lên server
            const updatedProduct = {
                ...product,
                productName: formData.productName,
                price: parseFloat(formData.price), // Chuyển giá về kiểu số
                brand: parseInt(formData.brand), // Chuyển brand về kiểu số
                model: formData.model,
                description: formData.description,
                fileName: formData.fileName,
                quantityInStock: parseInt(formData.quantityInStock),
                color: formData.color,
                storageCapacity: formData.storageCapacity,
                screenSize: formData.screenSize,
                ram: formData.ram,
                cpu: formData.cpu,
                os: formData.os,
                batteryCapacity: formData.batteryCapacity,
                productStatus: parseInt(formData.productStatus)
                // Thêm các trường khác cần thiết
            };
            // Gửi yêu cầu cập nhật đến server
            await axios.put(`http://localhost:8080/api/seller/product/${id}/edit`, updatedProduct);

            window.alert("Cập nhật sản phẩm thành công");
            window.location.href = "/admin/product";

        } catch (error) {
            console.error("Error during product update:", error);
        }
    };

    return (
        <>
            <AdminNavBar />
            <div className="container my-3 py-3">
                <h2 className="my-4">Chỉnh sửa Sản phẩm</h2>
                <form>
                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="brand">Thương hiệu</label>
                                <select type="text" className="form-control" id="brand" value={formData.brand} onChange={(e) => setFormData({ ...formData, brand: e.target.value })}>
                                    <option value="1">Apple</option>
                                    <option value="2">Samsung</option>
                                    <option value="3">Xiaomi</option>
                                    <option value="4">Oppo</option>
                                    <option value="5">Khác</option>
                                </select>
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="productName">Tên sản phẩm</label>
                                <input type="text" className="form-control" id="productName" value={formData.productName} onChange={(e) => setFormData({ ...formData, productName: e.target.value })} />
                            </div>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="model">Model</label>
                                <input type="text" className="form-control" id="model" value={formData.model} onChange={(e) => setFormData({ ...formData, model: e.target.value })} />
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="price">Giá</label>
                                <input type="number" className="form-control" id="price" value={formData.price} onChange={(e) => setFormData({ ...formData, price: e.target.value })} />
                            </div>
                        </div>
                    </div>

                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="quantityInStock">Số lượng</label>
                                <input type="number" className="form-control" id="quantityInStock" value={formData.quantityInStock} onChange={(e) => setFormData({ ...formData, quantityInStock: e.target.value })} />
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="color">Màu sắc</label>
                                <input type="text" className="form-control" id="color" value={formData.color} onChange={(e) => setFormData({ ...formData, color: e.target.value })} />
                            </div>
                        </div>
                    </div>

                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="storageCapacity">Bộ nhớ</label>
                                <input type="text" className="form-control" id="storageCapacity" value={formData.storageCapacity} onChange={(e) => setFormData({ ...formData, storageCapacity: e.target.value })} />
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="screenSize">Màn hình</label>
                                <input type="text" className="form-control" id="screenSize" value={formData.screenSize} onChange={(e) => setFormData({ ...formData, screenSize: e.target.value })} />
                            </div>
                        </div>
                    </div>

                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="ram">RAM</label>
                                <input type="text" className="form-control" id="ram" value={formData.ram} onChange={(e) => setFormData({ ...formData, ram: e.target.value })} />
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="cpu">CPU</label>
                                <input type="text" className="form-control" id="cpu" value={formData.cpu} onChange={(e) => setFormData({ ...formData, cpu: e.target.value })} />
                            </div>
                        </div>
                    </div>

                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="os">Hệ điều hành</label>
                                <input type="text" className="form-control" id="os" value={formData.os} onChange={(e) => setFormData({ ...formData, os: e.target.value })} />
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="batteryCapacity">Dung lượng pin</label>
                                <input type="text" className="form-control" id="batteryCapacity" value={formData.batteryCapacity} onChange={(e) => setFormData({ ...formData, batteryCapacity: e.target.value })} />
                            </div>
                        </div>
                    </div>

                    <div className='row'>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="productStatus">Trạng thái</label>
                                <select type="text" className="form-control" id="productStatus" value={formData.productStatus} onChange={(e) => setFormData({ ...formData, productStatus: e.target.value })}>
                                    <option value="0">Còn hàng</option>
                                    <option value="1">Hết hàng</option>
                                </select>
                            </div>
                        </div>
                        <div className='col-6'>
                            <div className="form-group">
                                <label htmlFor="fileName">Ảnh</label>
                                <input type="text" className="form-control" id="fileName" value={formData.fileName} onChange={(e) => setFormData({ ...formData, fileName: e.target.value })} />
                            </div>
                        </div>
                    </div>

                    <div className="form-group">
                        <label htmlFor="description">Mô tả</label>
                        <textarea type="text" className="form-control" id="description" value={formData.description} onChange={(e) => setFormData({ ...formData, description: e.target.value })} />
                    </div>

                    <button type="button" className="btn btn-primary" onClick={handleUpdate}>
                        Cập nhật
                    </button>
                    <Link to="/admin/product" className="btn btn-secondary ml-2">Quay lại</Link>
                </form>
            </div>
            <Footer />
        </>
    );
};

export default EditProduct;
