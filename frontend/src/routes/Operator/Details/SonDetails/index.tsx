import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as filhoService from "../../../../services/filhoService";
import { getLabel } from "../../../../models/enums/tipoFilho";
import { DFilho } from "../../../../models/filho";
import { formatDate } from "../../../../utils/formatters";

export default function SonDetails() {
  const params = useParams();
  const navigate = useNavigate();
  const [filho, setFilho] = useState<DFilho>();

  useEffect(() => {
    findFatherById();
  }, [params.sonId]);

  const findFatherById = () => {
    const sonId = Number(params.sonId);
    if (!isNaN(sonId)) {
      filhoService
        .pesquisarPorId(sonId)
        .then((response) => {
          setFilho(response.data);
        })
        .catch(() => {
          navigate("/sons");
        });
    } else {
      navigate("/sons");
    }
  };

  return (
    <main className="father-details-main">
      <section className="container">
        <div className="card father-card">
          <div className="father-header">
            <h1 className="father-title">
              {filho?.codigo} - {filho?.descricao}
            </h1>
          </div>
          <div className="father-info">
            <div className="info-item">
              <strong>Código:</strong> {filho?.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {filho?.descricao}
            </div>
            <div className="info-item">
              <strong>Pai:</strong> {filho?.pai ? <p onClick={() => (window.location.href = `/fathers/details/${filho?.pai.codigo}`)}
                  style={{ cursor: "pointer" }}>{filho?.pai.descricao}</p> : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Cor:</strong> {filho?.cor ? filho?.cor?.descricao : "Não especificada"}
            </div>
            <div className="info-item">
              <strong>Altura:</strong> {filho?.medidas ? filho?.medidas.altura : "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Largura:</strong> {filho?.medidas ? filho?.medidas.largura : "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Espessura:</strong> {filho?.medidas ? filho?.medidas.espessura : "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Roteiro:</strong> {filho?.roteiro ? <p onClick={() => (window.location.href = `/guides/details/${filho?.roteiro.codigo}`)}
                  style={{ cursor: "pointer" }}>{filho?.roteiro.descricao}</p> : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Implantação:</strong> {filho?.implantacao ? formatDate(filho.implantacao.toString()) : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Tipo:</strong> {filho?.tipo ? getLabel(filho.tipo) : "Não Especificado"}
            </div>
            <div className="info-item">
              <strong>Valor:</strong> R$ {filho?.valor}
            </div>
          </div>
        </div>
        
        <div className="sons-section">
          <h2>Materiais</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Descrição</th>
                <th>Quantidade Líquida</th>
                <th>Quantidade Bruta</th>
                <th>Valor (R$)</th>
              </tr>
            </thead>
            <tbody>
              {filho?.materiaisUsados.map((materialUsado) => (
                <tr
                    key={materialUsado.codigo}
                    onClick={() => (window.location.href = `/usedMaterials/${materialUsado.codigo}`)}
                    style={{ cursor: "pointer" }}
                >
                  <td>{materialUsado.material.codigo}</td>
                  <td>{materialUsado.material.descricao}</td>
                  <td>{materialUsado.quantidadeLiquida} {materialUsado.unidadeMedida}</td>
                  <td>{materialUsado.quantidadeBruta} {materialUsado.unidadeMedida}</td>
                  <td>{materialUsado.valor}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="sons-section">
          <h2>Acessórios</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Descrição</th>
                <th>Quantidade</th>
                <th>Valor (R$)</th>
              </tr>
            </thead>
            <tbody>
              {filho?.acessoriosUsados.map((acessorioUsado) => (
                <tr
                    key={acessorioUsado.codigo}
                    onClick={() => (window.location.href = `/usedAccessories/${acessorioUsado.codigo}`)}
                    style={{ cursor: "pointer" }}
                >
                  <td>{acessorioUsado.acessorio.codigo}</td>
                  <td>{acessorioUsado.acessorio.descricao}</td>
                  <td>{acessorioUsado.quantidade} {acessorioUsado.unidadeMedida}</td>
                  <td>{acessorioUsado.valor}</td>
                </tr>
              ))}
            </tbody>
          </table>
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
              {filho?.filhos.map((filho) => (
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