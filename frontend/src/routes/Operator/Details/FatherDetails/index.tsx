import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { DPai } from "../../../../models/pai";
import * as paiService from "../../../../services/paiService";
import './styles.css';
import { getLabel } from "../../../../models/enums/tipoPintura";

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
      <section id="details-section" className="container">
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
              <strong>Bordas - Largura:</strong> {pai?.bordasLargura || "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Número de Cantoneiras:</strong> {pai?.numeroCantoneiras || "Não especificado"}
            </div>
            <div className="info-item">
              <strong>TNT Uma Face:</strong> {pai?.tntUmaFace ? "Sim" : "Não"}
            </div>
            <div className="info-item">
              <strong>Plástico Acima:</strong> {pai?.plasticoAcima ? "Sim" : "Não"}
            </div>
            <div className="info-item">
              <strong>Plástico Adicional:</strong> {pai?.plasticoAdicional ? `${pai.plasticoAdicional} mm` : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Largura Plástico:</strong> {pai?.larguraPlastico || "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Faces:</strong> {pai?.faces || "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Especial:</strong> {pai?.especial ? "Sim" : "Não"}
            </div>
            <div className="info-item">
              <strong>Tipo:</strong> {pai?.tipoPintura ? getLabel(pai.tipoPintura) : "Não Especificado"}
            </div>
          </div>
        </div>
        <div className="sons-section mb20 mt20">
            <h2>Filhos</h2>
            <table className="table mb20 mt20">
                <thead>
                    <tr>
                        <th className="tb576">Código</th>
                        <th className="txt-left">Descrição</th>
                        <th className="txt-left">Cor</th>
                        <th className="txt-left">Medidas</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        pai?.filhos.map(filho => (
                            <tr key={filho.codigo} onClick={() => window.location.href = `/sons/details/${filho.codigo}`} style={{cursor: 'pointer'}}>
                                <td className="tb576">{filho.codigo}</td>
                                <td className="txt-left">{filho.descricao}</td>
                                <td className="txt-left">{filho.cor.descricao}</td>
                                <td className="txt-left">{filho.medidas.altura}X{filho.medidas.largura}X{filho.medidas.espessura}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
      </section>
    </main>
  );
}

