import { Layout } from "antd";
import { useState, useEffect } from "react";
import jwtDecode from "jwt-decode";
import axios from "axios";
const { Content } = Layout;
function SoldProducts() {
  const [soldProducts, setSoldProducts] = useState([]);
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
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/product/sold/${tokenData.id}`
      );
      setSoldProducts(res.data);
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
                <th scope="col">Product name</th>
                <th scope="col">Price</th>
                <th scope="col">Client name</th>
                <th scope="col">Shipping company name</th>
              </tr>
            </thead>
            <tbody>
              {soldProducts.map((soldProduct) => (
                <tr>
                  <td>{soldProduct.name}</td>
                  <td>{soldProduct.price}</td>
                  <td>{soldProduct.user.username}</td>
                  <td>{soldProduct.shippingCompanies.name}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Content>
  );
}
export default SoldProducts;
