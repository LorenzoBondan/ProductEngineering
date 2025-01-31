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
import List from './routes/CRUDs/MDP/Sheet/List';

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
                <Route index element={<List />} />
              </Route>
            </Route>


            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </HistoryRouter>
    </ContextToken.Provider>
  );
}
