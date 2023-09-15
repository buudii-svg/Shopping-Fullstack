import { Layout } from "antd";
import { useState, useEffect } from "react";
import jwtDecode from "jwt-decode";
import axios from "axios";
const { Content } = Layout;
function OfferedProducts() {
  const [offeredProducts, setOfferedProducts] = useState([]);
  const [tokenData, setTokenData] = useState(null);
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setTokenData(decodedToken);
    }
  }, []);
  const getProducts = async () => {
    if (tokenData.role === "selling") {
      const res = await axios.get(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/product/${tokenData.id}`
      );
      setOfferedProducts(res.data);
    }
  };

  useEffect(() => {
    getProducts();
  }, [tokenData]);
  return (
    <Content
      style={{
        padding: "50px 50px",
        width: "50%",
        margin: "auto",
      }}
    >
      <div className="card" style={{ color: "#fff" }}>
        <div className="card-header bg-light">
          <table className="table table-light table-hover">
            <thead>
              <tr>
                <th scope="col">name</th>
                <th scope="col">price</th>
                <th scope="col">Cart status</th>
              </tr>
            </thead>
            <tbody>
              {offeredProducts.map((offeredProduct) => (
                <tr>
                  <td>{offeredProduct.name}</td>
                  <td>{offeredProduct.price}</td>
                  <td>
                    {offeredProduct.atCart === false
                      ? "Not at cart yet"
                      : offeredProduct.atCart}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Content>
  );
}
export default OfferedProducts;
