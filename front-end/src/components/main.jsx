import React from "react";
import slideImg from "../assets/main.png";

const Home = () => {
  return (
    <>
      <div className="hero border-1 pb-3">
        <div className="card bg-dark text-white border-0 mx-3">
          <img
            className="card-img img-fluid"
            src={slideImg}
            alt="Card"
            height={500}
          />
          <div className="card-img-overlay d-flex align-items-center">
            <div className="container">
              <h3 className="card-title fs-1 text fw-lighter">Minh Nguyễn Store</h3>
              <h5 className="card-text fs-5 d-none d-sm-block ">
                Uy tín - Chất lượng - Tận tình.
              </h5>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;
