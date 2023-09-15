import { Layout } from "antd";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
const { Content } = Layout;
function ShippingCompnanies() {
  const [shippingCompanies, setShippingCompanies] = useState([]);
  useEffect(() => {
    const getProducts = async () => {
      const res = await axios.get(
        "http://localhost:8080/shopping-1.0-SNAPSHOT/api/shipping/company"
      );
      console.log(res.data);
      setShippingCompanies(res.data);
    };
    getProducts();
  }, []);
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
          <table class="table table-light table-hover">
            <thead>
              <tr>
                <th scope="col">Company name</th>
                <th scope="col">Company geographical coverage</th>
              </tr>
            </thead>
            <tbody>
              {shippingCompanies.map((shipping) => (
                <tr>
                  <td>{shipping.name}</td>
                  <td>{shipping.geographicalCoverage}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Content>
  );
}
export default ShippingCompnanies;
