
with Spica.Graphs;

generic
   type Vertex_Type is private;
   with package Graphs_Package is new CIS5050.Graphs(Vertex_Type);
package CIS5050.Graph_Algorithms is
   use Graphs_Package;

   subtype Extended_Vertex_Number_Type is
     Natural range 0 .. Vertex_Number_Type'Last;

   type Vertex_Information is
      record
         Distance    : Natural := 0;
         Predecessor : Extended_Vertex_Number_Type := 0; -- Zero means "no predecessor."
      end record;

   -- Describes a tree of vertexes.
   type Vertex_Tree is array(Vertex_Number_Type range <>) of Vertex_Information;

   -- Does a BreadthFirstSearch of the given Graph, starting at the given Vertex, and returns
   -- a description of the breadth first tree, with distances and predcessors filled in.
   function Breadth_First_Search
     (G : Graph; Start_Vertex : Vertex_Number_Type) return Vertex_Tree;

end Spica.Graph_Algorithms;
