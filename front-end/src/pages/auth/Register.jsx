import React, {useState} from 'react'
import { Footer, Navbar } from "../../components";
import { Link } from 'react-router-dom';
const Register = () => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [address, setAddress] = useState("");
    const [city, setCity] = useState("");
    const [country, setCountry] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();

        // Kiểm tra xác nhận mật khẩu
        if (password !== confirmPassword) {
            alert("Mật khẩu xác nhận không khớp");
            return;
        }

        if (!firstName || !lastName || !email || !password || !address || !city || !country || !postalCode || !phoneNumber) {
            window.alert("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/user/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    firstName,
                    lastName,
                    email,
                    password,
                    address,
                    city,
                    country,
                    postalCode,
                    phoneNumber,
                }),
            });

            if (response.ok) {
                const data = await response.json();
                window.alert("Đăng ký thành công");
                window.location.href = "/login";
                console.log(data);
            } else {
                const errorMessage = await response.text();
                setErrorMessage(errorMessage);
                alert("Đăng ký không thành công");
            }
        } catch (error) {
            console.error("Error during registration:", error);
        }
    };

    return (
        <>
            <Navbar />
            <div className="container my-3 py-3">
                <h1 className="text-center">ĐĂNG KÝ TÀI KHOẢN</h1>
                <hr />
                <div className="row my-4 h-100">
                    <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
                        <form onSubmit={handleRegister}>
                            <div className="form my-3">
                                <label form="Name">Họ</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="firstName"
                                    value={firstName}
                                    onChange={(e) => setFirstName(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Name">Tên đệm và tên</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="lastName"
                                    value={lastName}
                                    onChange={(e) => setLastName(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Email">Địa chỉ email</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    id="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Password">Mật khẩu</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Password">Xác nhận mật khẩu</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="confirmPassword"
                                    value={confirmPassword}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Address">Địa chỉ</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="address"
                                    value={address}
                                    onChange={(e) => setAddress(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="City">Thành phố</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="city"
                                    value={city}
                                    onChange={(e) => setCity(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Country">Quốc gia</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="country"
                                    value={country}
                                    onChange={(e) => setCountry(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Postal Code">Mã bưu điện</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="postalCode"
                                    value={postalCode}
                                    onChange={(e) => setPostalCode(e.target.value)}
                                />
                            </div>
                            <div className="form my-3">
                                <label form="Phone Number">Số điện thoại</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="phoneNumber"
                                    value={phoneNumber}
                                    onChange={(e) => setPhoneNumber(e.target.value)}
                                />
                            </div>
                            <div className="my-3">
                                <p>Đã có tài khoản <Link to="/login" className="text-decoration-underline text-info">Đăng nhập</Link> </p>
                            </div>
                            <div className="text-center">
                                <button className="my-2 mx-auto btn btn-dark" type="submit">
                                    Đăng ký
                                </button>
                            </div>
                        </form>
                        {errorMessage && (
                            <div className="alert alert-danger mt-3" role="alert">
                                {errorMessage}
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <Footer />
        </>
    )
}

export default Register