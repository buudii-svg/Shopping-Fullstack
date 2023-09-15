import { Layout } from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import jwtDecode from "jwt-decode";
const { Content } = Layout;
function Products() {
  const [products, setProducts] = useState([]);
  const [tokenData, setTokenData] = useState(null);
  const history = useHistory();
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setTokenData(decodedToken);
    }
  }, []);

  const getProducts = async () => {
    const res = await axios.get(
      "http://localhost:8080/shopping-1.0-SNAPSHOT/api/product"
    );
    console.log(res.data);
    setProducts(res.data);
  };

  const handleAddToCart = async (e, productID) => {
    e.preventDefault();
    if (tokenData.role === "Client") {
      await axios.post(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/cart/${productID}/${tokenData.id}`
      );
      alert("Product added to cart !");
      getProducts();
    } else {
      history.replace("/client/login");
    }
  };

  useEffect(() => {
    getProducts();
  }, []);

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
                  {tokenData?.role === "Client" ? (
                    <button
                      onClick={(e) => handleAddToCart(e, product.id)}
                      className="btn btn-primary"
                    >
                      Add to cart
                    </button>
                  ) : null}
                </div>
              </div>
            </div>
          ))}
        </div>
      </Content>
    </>
  );
}
export default Products;
