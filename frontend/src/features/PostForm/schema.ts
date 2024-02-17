import { GrindSize, RoastLevel } from "@/api/interfaces";
import { z } from "zod";

export const postFormSchema = z.object({
  location: z.string().optional(),
  origin: z
    .string({
      required_error: "必須項目です",
    })
    .min(1),
  wayToBrew: z.string().min(1).optional(),
  roastLevel: z.nativeEnum(RoastLevel).optional(),
  temperature: z
    .preprocess(
      (v) => Number(v),
      z
        .number()
        .min(0, "0以上で入力してください")
        .max(100, "100以下で入力してください")
    )
    .optional(),
  gramsOfCoffee: z
    .preprocess((v) => Number(v), z.number().min(0, "0以上で入力してください"))
    .optional(),
  gramsOfWater: z
    .preprocess((v) => Number(v), z.number().min(0, "0以上で入力してください"))
    .optional(),
  grindSize: z.nativeEnum(GrindSize).optional(),
  impression: z.string().min(0).max(255).optional(),
});
