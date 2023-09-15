import { Layout } from "antd";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
const { Content } = Layout;
function CustomerAccounts() {
  const [clients, setClients] = useState([]);
  useEffect(() => {
    const getProducts = async () => {
      const res = await axios.get(
        "http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/clients"
      );
      console.log(res.data);
      setClients(res.data);
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
                <th scope="col">Username</th>
                <th scope="col">Region</th>
                <th scope="col">Role</th>
              </tr>
            </thead>
            <tbody>
              {clients.map((client) => (
                <tr>
                  <td>{client.username}</td>
                  <td>{client.region}</td>
                  <td>{client.role}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Content>
  );
}
export default CustomerAccounts;
