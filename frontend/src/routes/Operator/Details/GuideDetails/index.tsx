import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as roteiroService from "../../../../services/roteiroService";
import { DRoteiro } from "../../../../models/roteiro";
import { formatDate } from "../../../../utils/formatters";

export default function GuideDetails() {

  const params = useParams();
  const navigate = useNavigate();
  const [roteiro, setRoteiro] = useState<DRoteiro>();

  useEffect(() => {
    findGuideById();
  }, [params.guideId]);

  const findGuideById = () => {
    const guideId = Number(params.guideId);
    if (!isNaN(guideId)) {
      roteiroService
        .pesquisarPorId(guideId)
        .then((response) => {
          setRoteiro(response.data);
        })
        .catch(() => {
          navigate("/guides");
        });
    } else {
      navigate("/guides");
    }
  };

  return (
    <main className="father-details-main">
      <section className="container">
        <div className="card father-card">
          <div className="father-header">
            <h1 className="father-title">
              {roteiro?.codigo} - {roteiro?.descricao}
            </h1>
          </div>
          <div className="father-info">
            <div className="info-item">
              <strong>Código:</strong> {roteiro?.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {roteiro?.descricao}
            </div>
            <div className="info-item">
              <strong>Implantação:</strong> {roteiro?.implantacao ? formatDate(roteiro?.implantacao.toString()) : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Data final:</strong> {roteiro?.dataFinal ? formatDate(roteiro?.dataFinal.toString()) : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Valor:</strong> {roteiro?.valor || "Não especificado"} mm
            </div>
          </div>
        </div>
        <div className="sons-section">
          <h2>Máquinas</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Tempo</th>
              </tr>
            </thead>
            <tbody>
              {roteiro?.roteiroMaquinas.map((roteiroMaquina) => (
                <tr
                  key={roteiroMaquina.codigo}
                  onClick={() => (window.location.href = `/guideMachines/${roteiroMaquina.codigo}`)}
                  style={{ cursor: "pointer" }}
                >
                  <td>{roteiroMaquina.maquina.codigo}</td>
                  <td>{roteiroMaquina.maquina.nome}</td>
                  <td>{roteiroMaquina.tempoMaquina}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </main>
  );
}