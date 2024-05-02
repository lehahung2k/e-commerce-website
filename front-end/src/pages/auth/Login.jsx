import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {Footer, Navbar} from "../../components";
import {useDispatch, useSelector} from "react-redux";
import {login} from "../../redux/action";

const Login = () => {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const userInfo = useSelector(state => state.authReducer).userInfo;

  const navigate = useNavigate();
  const handleLogin = async (e) => {
    e.preventDefault();
    if (!email || !password) {
      alert("Vui lòng nhập đầy đủ thông tin");
      return;
    }
    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({email, password}),
      });

      if (response.ok) {
        const data = await response.json();

        // Lưu token vào store hoặc localStorage
        dispatch(login(data.token, data));

      } else {
        alert("Sai email hoặc mật khẩu");
      }
    } catch (error) {
      console.error("Error during login:", error);
    }
  };

  useEffect(() => {
    if (userInfo.role === 'ADMIN') {
      navigate('/admin/product');
    } else if (userInfo.role === 'USER') {
      navigate('/');
    }
  }, [userInfo]);

  return (
      <>
        <Navbar/>
        <div className="container my-3 py-3">
          <h1 className="text-center">ĐĂNG NHẬP</h1>
          <hr/>
          <div className="row my-4 h-100">
            <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
              <form onSubmit={handleLogin}>
                <div className="my-3">
                  <label form="display-4">Địa chỉ email</label>
                  <input
                      type="email"
                      className="form-control"
                      id="floatingInput"
                      placeholder="name@example.com"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="my-3">
                  <label form="floatingPassword display-4">Mật khẩu</label>
                  <input
                      type="password"
                      className="form-control"
                      id="floatingPassword"
                      placeholder="******"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
                <div className="my-3">
                  <p>Chưa có tài khoản? <Link to="/register" className="text-decoration-underline text-info">Đăng ký
                    ngay</Link></p>
                </div>
                <div className="text-center">
                  <button className="my-2 mx-auto btn btn-dark" type="submit">
                    Đăng nhập
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <Footer/>
      </>
  );
};

export default Login;
