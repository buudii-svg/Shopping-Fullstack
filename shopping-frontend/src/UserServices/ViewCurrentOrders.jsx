import { Layout } from "antd";
import { useState, useEffect } from "react";
import jwtDecode from "jwt-decode";
import axios from "axios";
const { Content } = Layout;
function CurrentOrders() {
  const [currentOrders, setCurrentOrders] = useState([]);
  const [tokenData, setTokenData] = useState(null);
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode(token);
      setTokenData(decodedToken);
    }
  }, []);
  const getOrders = async () => {
    if (tokenData.role === "Client") {
      const res = await axios.get(
        `http://localhost:8080/shopping-1.0-SNAPSHOT/api/orders/${tokenData.id}`
      );
      setCurrentOrders(res.data);
      console.log(res.data);
    }
  };

  useEffect(() => {
    getOrders();
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
                <th scope="col">Products</th>
                <th scope="col">Region</th>
              </tr>
            </thead>
            <tbody>
              {currentOrders.map((currentOrder) =>
                currentOrder.statusOfTheOrder === "Current" ? (
                  <tr>
                    <td>
                      {currentOrder.products.map((product) => (
                        <div>
                          <p>{"Product name : " + product.name}</p>
                          <p>{"Product price : " + product.price}</p>
                        </div>
                      ))}
                    </td>
                    <td>{currentOrder.region}</td>
                  </tr>
                ) : null
              )}
            </tbody>
          </table>
        </div>
      </div>
    </Content>
  );
}
export default CurrentOrders;
