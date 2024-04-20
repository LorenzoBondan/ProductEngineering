import { Route, Switch } from "react-router-dom";
import List from "./List";
import Form from "./Form";

const Sheets = () => {
    return (
        <Switch>
          <Route path="/sheets" exact>
            <List/>
          </Route>
          <Route path="/sheets/:sheetId">
            <Form/>
          </Route>
        </Switch>
      );
}

export default Sheets;