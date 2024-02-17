import { GrindSize, RoastLevel } from "@/api/interfaces";
import { z } from "zod";

export const postFormSchema = z.object({
  location: z.string().optional(),
  origin: z.string().min(1),
  wayToBrew: z.string().min(1).optional(),
  roastLevel: z.nativeEnum(RoastLevel).optional(),
  temperature: z.number().min(0).max(100).optional(),
  gramsOfCoffee: z.number().min(0).optional(),
  gramsOfWater: z.number().min(0).optional(),
  grindSize: z.nativeEnum(GrindSize).optional(),
  impression: z.string().min(0).max(255).optional(),
});
