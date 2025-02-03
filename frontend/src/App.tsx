import { useEffect, useState } from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import { unstable_HistoryRouter as HistoryRouter } from 'react-router-dom';
import { history } from './utils/history';
import { PrivateRoute } from './components/PrivateRoute';
import { AccessTokenPayloadDTO } from './models/auth';
import { ContextToken } from './utils/context-token';
import * as authService from './services/authService';
import Auth from './routes/Auth';
import Operator from './routes/Operator';
import Operations from './routes/Operator/Operations';
import HomeBaseMaterials from './routes/Operator/HomeBaseMaterials';
import MDPPage from './routes/Operator/HomeBaseMaterials/MainComponents/MDPPage';
import MDFPage from './routes/Operator/HomeBaseMaterials/MainComponents/MDFPage';
import AluminiumPage from './routes/Operator/HomeBaseMaterials/MainComponents/AluminiumPage';
import PackagingPage from './routes/Operator/HomeBaseMaterials/MainComponents/PackagingPage';
import MachinePage from './routes/Operator/HomeBaseMaterials/MainComponents/MachinePage';
import PublicPage from './routes/Operator/HomeBaseMaterials/MainComponents/PublicPage';
import HomeItems from './routes/Operator/HomeItems';
import HomeStruct from './routes/Operator/HomeStructs';
import Sheets from './routes/CRUDs/MDP/Sheet';
import SheetForm from './routes/CRUDs/MDP/Sheet/SheetForm';
import SheetList from './routes/CRUDs/MDP/Sheet/SheetList';
import Glues from './routes/CRUDs/MDP/Glue';
import GlueList from './routes/CRUDs/MDP/Glue/GlueList';
import GlueForm from './routes/CRUDs/MDP/Glue/GlueForm';
import EdgeBandings from './routes/CRUDs/MDP/EdgeBanding';
import EdgeBandingList from './routes/CRUDs/MDP/EdgeBanding/EdgeBandingList';
import EdgeBandingForm from './routes/CRUDs/MDP/EdgeBanding/EdgeBandingForm';
import Paintings from './routes/CRUDs/MDF/Painting';
import PaintingList from './routes/CRUDs/MDF/Painting/PaintingList';
import PaintingForm from './routes/CRUDs/MDF/Painting/PaintingForm';
import PaintingBorderBackgroundForm from './routes/CRUDs/MDF/PaintingBorderBackground/PaintingBorderBackgroundForm';
import PaintingBorderBackgrounds from './routes/CRUDs/MDF/PaintingBorderBackground';
import PaintingBorderBackgroundList from './routes/CRUDs/MDF/PaintingBorderBackground/PaintingBorderBackgroundList';
import Polyesters from './routes/CRUDs/MDF/Polyester';
import PolyesterList from './routes/CRUDs/MDF/Polyester/PolyesterList';
import PolyesterForm from './routes/CRUDs/MDF/Polyester/PolyesterForm';
import Colors from './routes/CRUDs/Public/Color';
import ColorList from './routes/CRUDs/Public/Color/ColorList';
import ColorForm from './routes/CRUDs/Public/Color/ColorForm';
import CornerBrackets from './routes/CRUDs/Packaging/CornerBracket';
import CornerBracketList from './routes/CRUDs/Packaging/CornerBracket/CornerBracketList';
import CornerBracketForm from './routes/CRUDs/Packaging/CornerBracket/CornerBracketForm';

export default function App() {

  const [contextTokenPayload, setContextTokenPayload] = useState<AccessTokenPayloadDTO>();

  useEffect(() => {

    if (authService.isAuthenticated()) {
      const payload = authService.getAccessTokenPayload();
      setContextTokenPayload(payload);
    }
  }, []);

  return (
    <ContextToken.Provider value={{ contextTokenPayload, setContextTokenPayload }}>
        <HistoryRouter history={history}>
          <Routes>
            <Route path="/login" element={<Auth />} />

            <Route path="/" element={<PrivateRoute roles={['ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR']}><Operator /></PrivateRoute>} >
              <Route index element={<Operations />} />
              <Route path="operations" element={<Operations />} />

              <Route path="homestructs" element={<HomeStruct />} />
              <Route path="homeitems" element={<HomeItems />} />

              <Route path="homebasematerials" element={<HomeBaseMaterials />} />
              <Route path="homebasematerials/mdp" element={<MDPPage />} />
              <Route path="homebasematerials/mdf" element={<MDFPage />} />
              <Route path="homebasematerials/aluminium" element={<AluminiumPage />} />
              <Route path="homebasematerials/packaging" element={<PackagingPage />} />
              <Route path="homebasematerials/machines" element={<MachinePage />} />
              <Route path="homebasematerials/public" element={<PublicPage />} />

              <Route path="sheets" element={<Sheets />}>
                <Route index element={<SheetList />} />
                <Route path=":sheetId" element={<SheetForm />} />
              </Route>

              <Route path="edgebandings" element={<EdgeBandings />}>
                <Route index element={<EdgeBandingList />} />
                <Route path=":edgeBandingId" element={<EdgeBandingForm />} />
              </Route>

              <Route path="glues" element={<Glues />}>
                <Route index element={<GlueList />} />
                <Route path=":glueId" element={<GlueForm />} />
              </Route>

              <Route path="paintings" element={<Paintings />}>
                <Route index element={<PaintingList />} />
                <Route path=":paintingId" element={<PaintingForm />} />
              </Route>

              <Route path="paintingborderbackgrounds" element={<PaintingBorderBackgrounds />}>
                <Route index element={<PaintingBorderBackgroundList />} />
                <Route path=":paintingBorderBackgroundId" element={<PaintingBorderBackgroundForm />} />
              </Route>

              <Route path="polyesters" element={<Polyesters />}>
                <Route index element={<PolyesterList />} />
                <Route path=":polyesterId" element={<PolyesterForm />} />
              </Route>

              <Route path="cornerbrackets" element={<CornerBrackets />}>
                <Route index element={<CornerBracketList />} />
                <Route path=":cornerBracketId" element={<CornerBracketForm />} />
              </Route>

              <Route path="colors" element={<Colors />}>
                <Route index element={<ColorList />} />
                <Route path=":colorId" element={<ColorForm />} />
              </Route>

            </Route>

            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </HistoryRouter>
    </ContextToken.Provider>
  );
}
