import { useCallback } from "react";
import { API_BASE_URL } from "./config";
import { useToken } from "@/hook/useToken";

type Props<T> = {
  path: `/${string}`;
  payload?: T;
};
export const useApi = () => {
  const [localStorage] = useToken();

  const api = useCallback(
    async <Req, Res>(
      props: Props<Req>,
      method: "GET" | "POST" | "PUT" = "POST",
    ) => {
      switch (method) {
        case "POST":
        case "PUT": {
          const res = await fetch(`${API_BASE_URL}${props.path}`, {
            method,
            body: JSON.stringify(props.payload),
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage?.token}`,
            },
          });

          return res.json() as Res;
        }

        case "GET": {
          const res = await fetch(`${API_BASE_URL}${props.path}`, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage?.token}`,
            },
          });

          return res.json() as Res;
        }
      }
    },
    [],
  );

  return { api };
};
