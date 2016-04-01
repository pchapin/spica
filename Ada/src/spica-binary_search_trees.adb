---------------------------------------------------------------------------
-- FILE    : spica-binary_search_trees.adb
-- SUBJECT : Package providing general binary search trees.
-- AUTHOR  : (C) Copyright 2012 by Peter C. Chapin
--
-- Please send comments or bug reports to
--
--      Peter C. Chapin <PChapin@vtc.vsc.edu>
---------------------------------------------------------------------------
with Ada.Unchecked_Deallocation;

package body Spica.Binary_Search_Trees is

   procedure Deallocate_Node is new
      Ada.Unchecked_Deallocation(Object => Tree_Node, Name => Tree_Node_Access);

   -- Searches a subtree looking for the given item. Returns pointer to node or null.
   function Search
     (Pointer : in Tree_Node_Access; Item : Element_Type) return Tree_Node_Access is
   begin
      if Pointer = null then return null; end if;
      if Item = Pointer.Data then
         return Pointer;
      elsif Item < Pointer.Data then
         return Search(Pointer.Left_Child, Item);
      else
         return Search(Pointer.Right_Child, Item);
      end if;
   end Search;


   -- Inserts Item into tree T. Duplicate items overwrite existing tree elements.
   procedure Insert(T : in out Tree; Item : in Element_Type) is
      New_Node : Tree_Node_Access := new Tree_Node'(Data => Item, others => <>);

      procedure Search(Pointer : in not null Tree_Node_Access; Inserted : out Boolean) is
      begin
         if Item = Pointer.Data then
            Pointer.Data := Item;
            Inserted := False;
         elsif Item < Pointer.Data then
            if Pointer.Left_Child = null then
               Pointer.Left_Child := New_Node;
               New_Node.Parent := Pointer;
               Inserted := True;
            else
               Search(Pointer.Left_Child, Inserted);
            end if;
         else
            if Pointer.Right_Child = null then
               Pointer.Right_Child := New_Node;
               New_Node.Parent := Pointer;
               Inserted := True;
            else
               Search(Pointer.Right_Child, Inserted);
            end if;
         end if;
      end Search;

      Advance_Count : Boolean;
   begin
      if T.Root = null then
         T.Root := New_Node;
         T.Count := 1;
      else
         Search(T.Root, Inserted => Advance_Count);
         if Advance_Count then T.Count := T.Count + 1; end if;
      end if;
   end Insert;


   -- Returns True if Item is in the tree, False otherwise. If found replace Item from tree.
   procedure Lookup(T : in out Tree; Item : in out Element_Type; Result : out Boolean) is
      Matched_Node : Tree_Node_Access;
   begin
      Result := False;
      Matched_Node := Search(T.Root, Item);
      if Matched_Node /= null then
         Result := True;
         Item   := Matched_Node.Data;
      end if;
   end Lookup;


   -- Deletes Item from tree T. If Item is not in T, there is no effect.
   procedure Delete(T : in out Tree; Item : in Element_Type) is
      Kill_Me : Tree_Node_Access := Search(T.Root, Item);


      function Minimum(Root : not null Tree_Node_Access) return Tree_Node_Access is
         P : Tree_Node_Access := Root;
      begin
         while P.Left_Child /= null loop
            P := P.Left_Child;
         end loop;
         return P;
      end Minimum;


      procedure Remove(Kill_Me : in out Tree_Node_Access) is
      begin
         if Kill_Me.Left_Child = null then
            -- At most one child (the right child may or may not be null).
            if Kill_Me = T.Root then
               T.Root := Kill_Me.Right_Child;
               if Kill_Me.Right_Child /= null then
                  Kill_Me.Right_Child.Parent := null;
               end if;
            else
               if Kill_Me = Kill_Me.Parent.Left_Child then
                  Kill_Me.Parent.Left_Child := Kill_Me.Right_Child;
               else
                  Kill_Me.Parent.Right_Child := Kill_Me.Right_Child;
               end if;
               if Kill_Me.Right_Child /= null then
                  Kill_Me.Right_Child.Parent := Kill_Me.Parent;
               end if;
            end if;
            Deallocate_Node(Kill_Me);

         elsif Kill_Me.Right_Child = null then
            -- At most one child (the left child is definitely not null).
            if Kill_Me = T.Root then
               T.Root := Kill_Me.Left_Child;
               Kill_Me.Left_Child.Parent := null;
            else
               if Kill_Me = Kill_Me.Parent.Left_Child then
                  Kill_Me.Parent.Left_Child := Kill_Me.Left_Child;
               else
                  Kill_Me.Parent.Right_Child := Kill_Me.Left_Child;
               end if;
               Kill_Me.Left_Child.Parent := Kill_Me.Parent;
            end if;
            Deallocate_Node(Kill_Me);

         else
            -- Both children are non-null.
            declare
               Other : Tree_Node_Access := Minimum(Kill_Me.Right_Child);
               Temp  : Element_Type;
            begin
               Temp         := Kill_Me.Data;
               Kill_Me.Data := Other.Data;
               Other.Data   := Temp;
               Remove(Other);
            end;
         end if;
      end Remove;

   begin
      if Kill_Me = null then return; end if;
      Remove(Kill_Me);
      T.Count := T.Count - 1;
   end Delete;


   -- Returns the root element.
   function Get_Root(T : Tree) return Element_Type is
   begin
      if T.Root = null then
         raise Constraint_Error;
      end if;
      return T.Root.Data;
   end Get_Root;


   -- Returns the number of elements in the tree.
   function Size(T : Tree) return Natural is
   begin
      return T.Count;
   end Size;


   -- For debugging. Raises Inconsistent_Tree if a problem is found.
   procedure Check_Sanity(T : in Tree) is

      function Count_Nodes(Pointer : Tree_Node_Access) return Natural is
      begin
         if Pointer = null then return 0; end if;
         return 1 + Count_Nodes(Pointer.Left_Child) + Count_Nodes(Pointer.Right_Child);
      end Count_Nodes;

      procedure Check_Structure(Pointer : in not null Tree_Node_Access) is
      begin
         if Pointer.Left_Child /= null then
            if Pointer.Left_Child.Parent /= Pointer then
               raise Trees_Package.Inconsistent_Tree;
            end if;
            Check_Structure(Pointer.Left_Child);
         end if;
         if Pointer.Right_Child /= null then
            if Pointer.Right_Child.Parent /= Pointer then
               raise Trees_Package.Inconsistent_Tree;
            end if;
            Check_Structure(Pointer.Right_Child);
         end if;
      end Check_Structure;

      procedure Check_Ordering(Pointer : in not null Tree_Node_Access) is
      begin
         if Pointer.Left_Child /= null then
            if not (Pointer.Left_Child.Data < Pointer.Data or
                    Pointer.Left_Child.Data = Pointer.Data) then
               raise Trees_Package.Inconsistent_Tree;
            end if;
            Check_Ordering(Pointer.Left_Child);
         end if;
         if Pointer.Right_Child /= null then
            if not (Pointer.Data < Pointer.Right_Child.Data or
                    Pointer.Data = Pointer.Right_Child.Data) then
               raise Trees_Package.Inconsistent_Tree;
            end if;
            Check_Ordering(Pointer.Right_Child);
         end if;
      end Check_Ordering;


   begin
      if  Count_Nodes(T.Root) /= T.Count then
         raise Trees_Package.Inconsistent_Tree;
      end if;
      if T.Root /= null then
         Check_Structure(T.Root);
         Check_Ordering(T.Root);
      end if;
   end Check_Sanity;


   procedure Finalize(T : in out Tree) is

      procedure Deallocate_Subtree(Pointer : in out Tree_Node_Access) is
      begin
         if Pointer = null then return; end if;
         Deallocate_Subtree(Pointer.Left_Child);
         Deallocate_Subtree(Pointer.Right_Child);
         Deallocate_Node(Pointer);
      end Deallocate_Subtree;

   begin
      Deallocate_Subtree(T.Root);
      T.Count := 0;
   end Finalize;

end Spica.Binary_Search_Trees;
