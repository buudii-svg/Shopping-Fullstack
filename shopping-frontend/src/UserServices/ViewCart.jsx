import { Layout, message } from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import jwtDecode from "jwt-decode";
const { Content } = Layout;
function ViewCart() {
  const [products, setProducts] = useState([]);
  const [tokenData, setTokenData] = useState(null);
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setTokenData(decodedToken);
    }
  }, []);

  const getProducts = async () => {
    if (tokenData.role === "Client") {
      const res = await axios.get(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/cart/products/${tokenData.id}`
      );
      console.log(res.data);
      setProducts(res.data);
    } else {
      alert("Unauthorized !");
    }
  };

  const handleRemoveFromCart = async (e, productID) => {
    e.preventDefault();
    if (tokenData.role === "Client") {
      await axios.post(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/cart/remove/${productID}`
      );
      alert("Product removed from cart !");
      getProducts();
    } else {
      alert("Unauthorized !");
    }
  };

  useEffect(() => {
    getProducts();
  }, [tokenData]);

  const handleCheckout = async (e) => {
    e.preventDefault();
    if (tokenData.role === "Client") {
      await axios.post(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/cart/checkout/${tokenData.id}`
      );
      const res = await axios.get(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/notification/${tokenData.id}`
      );
      alert(res.data.message);
      getProducts();
    } else {
      alert("Unauthorized !");
    }
  };

  return (
    <>
      <Content
        style={{
          padding: "50px 50px",
        }}
      >
        <div className="row">
          {products.map((product) => (
            <div className="col-md-2">
              <div className="card">
                <div className="card-body text-center">
                  <h5 className="card-title">{product.name}</h5>{" "}
                  <p className="card-text">Price : {product.price}</p>
                  <button
                    onClick={(e) => handleRemoveFromCart(e, product.id)}
                    className="btn btn-danger"
                  >
                    Remove from cart
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
        {products.length > 0 ? (
          <>
            <button onClick={handleCheckout} className="btn mt-2 btn-primary">
              Checkout
            </button>
          </>
        ) : null}
      </Content>
    </>
  );
}
export default ViewCart;
