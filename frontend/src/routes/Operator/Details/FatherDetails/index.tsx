import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { DPai } from "../../../../models/pai";
import * as paiService from "../../../../services/paiService";
import { getLabel } from "../../../../models/enums/tipoPintura";
import { FaCheckCircle, FaTimesCircle } from "react-icons/fa";
import "./styles.css";

export default function FatherDetails() {
  const params = useParams();
  const navigate = useNavigate();
  const [pai, setPai] = useState<DPai>();

  useEffect(() => {
    findFatherById();
  }, [params.fatherId]);

  const findFatherById = () => {
    const fatherId = Number(params.fatherId);
    if (!isNaN(fatherId)) {
      paiService
        .pesquisarPorId(fatherId)
        .then((response) => {
          setPai(response.data);
        })
        .catch(() => {
          navigate("/fathers");
        });
    } else {
      navigate("/fathers");
    }
  };

  return (
    <main className="father-details-main">
      <section className="container">
        <div className="card father-card">
          <div className="father-header">
            <h1 className="father-title">
              {pai?.codigo} - {pai?.descricao}
            </h1>
          </div>
          <div className="father-info">
            <div className="info-item">
              <strong>Código:</strong> {pai?.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {pai?.descricao}
            </div>
            <div className="info-item">
              <strong>Modelo:</strong> {pai?.modelo?.descricao || "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Categoria Componente:</strong> {pai?.categoriaComponente?.descricao || "Não especificada"}
            </div>
            <div className="info-item">
              <strong>Bordas - Comprimento:</strong> {pai?.bordasComprimento || "Não especificado"} mm
            </div>
            <div className="info-item">
              <strong>TNT Uma Face:</strong> {pai?.tntUmaFace ? <FaCheckCircle className="icon success" /> : <FaTimesCircle className="icon error" />}
            </div>
            <div className="info-item">
              <strong>Plástico Acima:</strong> {pai?.plasticoAcima ? <FaCheckCircle className="icon success" /> : <FaTimesCircle className="icon error" />}
            </div>
            <div className="info-item">
              <strong>Tipo:</strong> {pai?.tipoPintura ? getLabel(pai.tipoPintura) : "Não Especificado"}
            </div>
          </div>
        </div>
        <div className="sons-section">
          <h2>Filhos</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Descrição</th>
                <th>Cor</th>
                <th>Medidas</th>
              </tr>
            </thead>
            <tbody>
              {pai?.filhos.map((filho) => (
                <tr
                  key={filho.codigo}
                  onClick={() => (window.location.href = `/sons/details/${filho.codigo}`)}
                  style={{ cursor: "pointer" }}
                >
                  <td>{filho.codigo}</td>
                  <td>{filho.descricao}</td>
                  <td>{filho.cor.descricao}</td>
                  <td>
                    {filho.medidas.altura} x {filho.medidas.largura} x {filho.medidas.espessura}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </main>
  );
}